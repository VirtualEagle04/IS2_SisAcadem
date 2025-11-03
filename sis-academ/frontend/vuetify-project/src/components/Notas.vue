<template>
  <v-data-table
    :headers="headers"
    :items="items"
    :loading="loading"
    density="comfortable"
    return-object
    v-model="selected"
    show-select
    :search="search"
    :show-select="!soloLectura"
    multi-sort
  >
    <template v-slot:top>
      <v-toolbar flat class="rounded">
        <v-toolbar-title>
          <v-icon start>mdi-numeric-5-box-multiple</v-icon>
          Gestión de Notas
        </v-toolbar-title>
        <v-text-field
          v-model="search"
          label="Buscar"
          prepend-inner-icon="mdi-magnify"
          variant="outlined"
          hide-details
          single-line
        ></v-text-field>
        <v-btn v-if="!soloLectura" class="me-2" prepend-icon="mdi-file-pdf-box" color="orange" @click="generateBoletines">
          Generar Boletines
        </v-btn>
        <v-btn v-if="!soloLectura" class="me-2" prepend-icon="mdi-calculator" color="gray" @click="handleCalculation">
          Cálcular Notas Finales
        </v-btn>
        <v-btn v-if="!soloLectura" class="me-2" prepend-icon="mdi-plus" color="green" @click="add">
          Agregar una Nota
        </v-btn>
        <v-btn
          v-if="!soloLectura"
          class="me-2"
          prepend-icon="mdi-delete"
          color="red"
          @click="handleConfirm"
          :disabled="selected.length === 0"
        >
          Eliminar Notas
        </v-btn>
      </v-toolbar>
    </template>
    
    <template v-slot:item.calificacion="{ item }">
      <v-chip v-if="item.calificacion <= 1.0" color="red">{{ item.calificacion }}</v-chip>
      <v-chip v-if="item.calificacion <= 2.0 && item.calificacion > 1.0" color="deep-orange">{{ item.calificacion }}</v-chip>
      <v-chip v-if="item.calificacion <= 3.0 && item.calificacion > 2.0" color="yellow">{{ item.calificacion }}</v-chip>
      <v-chip v-if="item.calificacion <= 4.0 && item.calificacion > 3.0" color="blue">{{ item.calificacion }}</v-chip>
      <v-chip v-if="item.calificacion <= 5.0 && item.calificacion > 4.0" color="green">{{ item.calificacion }}</v-chip>
    </template>
    
    <template v-slot:item.tipo="{ item }">
      <v-chip v-if="item.tipo === 'Normal'" color="secondary" variant="tonal">{{ item.tipo }}</v-chip>
      <v-chip v-if="item.tipo === 'Definitiva'" color="" variant="flat">{{ item.tipo }}</v-chip>
    </template>

    <template v-slot:item.acciones="{ item }">
      <div class="d-flex ga-2">
        <v-icon
          v-if="!soloLectura"
          color="blue"
          icon="mdi-pencil"
          size="small"
          @click="edit(item)"
        ></v-icon>

        <v-icon
          v-if="!soloLectura"
          color="red"
          icon="mdi-delete"
          size="small"
          @click="handleConfirm(item.idNota)"
        ></v-icon>
      </div>
    </template>
  </v-data-table>

  <v-dialog v-model="dialog" max-width="500">
    <v-card class="pb-5 pl-5 pr-5" :title="`${editing ? 'Editar' : 'Agregar'} una Nota`">
      
      <v-form @submit.prevent="handleSubmit">
        
        <v-select
          v-model="record.idActividad"
          label="Actividad"
          :items="actividades"
          item-title="nombre"
          item-value="idActividad"
        ></v-select>
        <v-select
          v-model="record.idEstudiante"
          label="Estudiante"
          :items="estudiantes"
          item-title="docIdentidad"
          item-value="idUsuario"
        ></v-select>
        <v-select
          v-model="record.idPeriodo"
          label="Periodo Académico"
          :items="periodos"
          item-title="nombre"
          item-value="idPeriodo"
        ></v-select>
        <v-number-input
          v-model="record.calificacion"
          label="Calificacion"
          :rules="[rules.required]"
          :min="0.0"
          :max="5.0"
          precision="1"
        ></v-number-input>
        <v-text-field
          v-model="record.observacion"
          label="Observaciones"
        ></v-text-field>
        <v-select
          v-model="record.tipo"
          label="Tipo"
          :items="tipos"
          item-title="value"
          item-value="value"
        ></v-select>
        
        <v-btn class="mt-2" @click="dialog = false" block>Cancelar</v-btn>
        <v-btn class="mt-2" type="submit" block color="green">Aceptar</v-btn>
      </v-form>
      
    </v-card>
  </v-dialog>
  
  <v-dialog v-model="confirmDialog.enabled" max-width="500">
    <v-card :title="`¿Eliminar ${selected.length === 0 ? 'la Nota' : 'todas las Notas'}?`" 
      text="¿Confirma que quiere eliminar el/los registro(s)?"
    >
    <template v-slot:prepend>
      <v-icon color="warning" icon="mdi-alert"></v-icon>
    </template>
      <v-card-actions class="bg-surface-light">
        <v-btn text="Cancelar" variant="plain" @click="confirmDialog.enabled = false; selected = [];"></v-btn>
        <v-spacer></v-spacer>
        <v-btn text="Confirmar" @click="`${selected.length === 0 ? remove(confirmDialog.id) : removeSelected()}`"></v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

  <v-snackbar v-model="snackbar" :color="snackbarColor" timeout="3000">
    {{ snackbarMsg }}
  </v-snackbar>
</template>

<script setup>
import { ref, onMounted, shallowRef, computed } from "vue";
import axios from "axios";
import jsPDF from "jspdf";
import autoTable from "jspdf-autotable";

const userData = ref(null);
const soloLectura = computed(() => {
  if (!userData.value) return false;
  return userData.value.permisos?.soloLectura || false;
});

const API_URL_MATERIAS = "http://localhost:8080/api/materias";
const materias = ref([]);

const API_URL_ACTIVIDADES = "http://localhost:8080/api/actividades";
const actividades = ref([]);

const API_URL_ESTUDIANTES = "http://localhost:8080/api/usuarios/usuarios";
const estudiantes = ref([]);

const API_URL_ESTUDIANTES_DETALLES = "http://localhost:8080/api/usuarios/estudiantes-detalles";
const estudiantesDetalles = ref([]);

const API_URL_PERIODOS = "http://localhost:8080/api/horarios/periodos-academicos";
const periodos = ref([]);

const API_URL_NOTAS = "http://localhost:8080/api/notas"
const items = ref([]);
const loading = ref(true);
const selected = ref([]);
const headers = [
  { title: "ID", key: "idNota" },
  // { title: "ID Actividad", key: "idActividad" },
  { title: "Actividad", key: "nombreActividad" },
  // { title: "ID Estudiante", key: "idEstudiante" },
  { title: "Estudiante", key: "docEstudiante" },
  // { title: "ID Periodo", key: "idPeriodo" },
  { title: "Periodo", key: "nombrePeriodo" },
  { title: "Calificación", key: "calificacion" },
  { title: "Observaciones", key: "observacion" },
  { title: "Tipo", key: "tipo" },
  { title: "Acciones", key: "acciones" },
];
const FORM_DATA = {
  idNota: null,
  idActividad: null,
  idEstudiante: null,
  idPeriodo: null,
  calificacion: null,
  observacion: null,
  tipo: "",
};
const search = ref('');
const tipos = ref([
  {value: 'Normal'},
  {value: 'Definitiva'}
]);

const snackbar = ref(false);
const snackbarColor = ref("success");
const snackbarMsg = ref("");

function showSnackbar(msg, color = "success") {
  snackbarMsg.value = msg;
  snackbarColor.value = color;
  snackbar.value = true;
}

const valid = ref(true);
const record = ref(FORM_DATA);
const dialog = shallowRef(false);
const editing = shallowRef(false);
const confirmDialog = ref({
  enabled: false,
  id: null
});
const rules = {
  required: value => !!value || 'Campo requerido'
};

function add() {
  editing.value = false;
  record.value = { ...FORM_DATA };
  dialog.value = true;
}

function edit(item) {
  editing.value = true;
  record.value = { ...item };
  dialog.value = true;
}

async function remove(id) {
  await axios
    .delete(`${API_URL_NOTAS}/delete/${id}`)
    .then((res) => {
      showSnackbar(res.data);
    })
    .catch((error) => {
      showSnackbar(error.response.data, "error");
    });
  fetchAll();
  confirmDialog.value.enabled = false;
  confirmDialog.value.id = null;
}

async function removeSelected() {
  if (selected.length === 0) return;

  try {
    await Promise.all(
      selected.value.map((nota) =>
        axios.delete(`${API_URL_NOTAS}/delete/${nota.idNota}`)
      )
    );
    showSnackbar("Notas eliminadas correctamente");
    selected.value = [];
  } catch (error) {
    showSnackbar("Error al eliminar las notas", "error");
  }
  fetchAll();
  confirmDialog.value.enabled = false;
  confirmDialog.value.id = null;
}

async function save() {
  if (editing.value) {
    await axios
      .put(`${API_URL_NOTAS}/update/${record.value.idNota}`, record.value)
      .then((res) => {
        showSnackbar(res.data);
        dialog.value = false;
      })
      .catch((error) => {
        showSnackbar(error.response.data, "error");
      });
  } else {
    await axios
      .post(`${API_URL_NOTAS}/create`, record.value)
      .then((res) => {
        showSnackbar(res.data);
        dialog.value = false;
      })
      .catch((error) => {
        showSnackbar(error.response.data, "error");
      });
  }
  fetchAll();
}

function handleConfirm(id) {
  confirmDialog.value.enabled = true;
  confirmDialog.value.id = id;
}

function handleSubmit(e) {
  valid.value = true;
  console.log(e)
  if (e.target[3].value === "") valid.value = false;
  
  if (valid.value) {
    save();
  } else {
    showSnackbar("Campos incompletos", "error");
  }
  
  valid.value = true;
}

async function handleCalculation() {
  // Para cada Estudiante (e)
    // Filtar todas las Notas de ese Estudiante (notasEstudiante)
    // Por cada Materia (m)
      // Filtrar todas las Actividades de esa Materia (actividadesMateria)
      // Filtrar todas las Notas con esos IDs de Actividades (notasMateria)
      // Por cada Nota de notasMateria (n)
        // 1. n.calificacion * (n.idActividad.porcentaje/100)
        // 2. Sumar resultado a variable (finalMateria)
      // Crear una Nota con: idActividad null, calificacion (finalMateria), observación "Nombre Materia", tipo "Final"
      
  estudiantes.value.forEach(e => {
    if (e.idRol === 5) {
      const notasEstudiante = items.value.filter(nota => nota.idEstudiante === e.idUsuario);
      
      materias.value.forEach(async m => {
        const actividadesMateria = actividades.value.filter(actividad => actividad.idMateria === m.idMateria);
        const idsActividadesMateria = actividadesMateria.map(a => a.idActividad);
        
        const notasMateriaEstudiante = notasEstudiante.filter(ne => {
          return idsActividadesMateria.includes(ne.idActividad);
        });
        
        const finalMateria = ref(0);
        
        notasMateriaEstudiante.forEach(n => {
          const actividad = actividadesMateria.find(a => a.idActividad === n.idActividad);
          finalMateria.value += (n.calificacion * (actividad.porcentaje/100));
        });
        
        finalMateria.value = Math.round(finalMateria.value * 10) / 10;
        
        // console.log(`Estudiante: ${e.nombres}, Nota Final Materia ${m.nombre}: ${finalMateria.value}`);
        
        const notaFinalData = {
          idNota: null,
          idActividad: null,
          idEstudiante: e.idUsuario,
          idPeriodo: 4,
          calificacion: finalMateria.value,
          observacion: `${m.nombre}`,
          tipo: "Definitiva"
        };
        
        await axios
          .post(`${API_URL_NOTAS}/create`, notaFinalData)
          .then((res) => {
            // showSnackbar(`Estudiante: ${e.nombres}, Nota Final Materia ${m.nombre}: ${finalMateria.value}`);
          })
          .catch((error) => {
            showSnackbar(error.response.data, "error");
          });
      });
    }
  });
  
  showSnackbar("Notas Definitivas calculadas con éxito.")
    
  fetchAll();
}

function generateBoletines() {
  const definitivas = items.value.filter(n => n.tipo === 'Definitiva');
  if (definitivas.length === 0) {
    showSnackbar("No se han podido generar los boletines porque no hay notas definitivas", "error");
    return;
  }
  
  const grouped = definitivas.reduce((acc, nota) => {
    if (!acc[nota.idEstudiante]) acc[nota.idEstudiante] = [];
    acc[nota.idEstudiante].push(nota);
    return acc;
  }, {});
  
  Object.entries(grouped).forEach(([idEstudiante, notas]) => {
    const estudiante = estudiantes.value.find(e => String(e.idUsuario) === String(idEstudiante));
    console.log(estudiante);
    const nombre = estudiante ? estudiante.nombres + " " + (estudiante.apellidos || "") : "Estudiante desconocido";
    const doc = estudiante?.docIdentidad || "N/A";
    
    // Crear documento
    const pdf = new jsPDF();
    pdf.setFontSize(18);
    pdf.text("Boletín Académico", 105, 20, {align: "center"});
    
    pdf.setFontSize(12);
    pdf.text(`Nombre: ${nombre}`, 20, 35);
    pdf.text(`Documento: ${doc}`, 20, 43);
    pdf.text(`Periodo Académico: ${notas[0]?.nombrePeriodo || "N/A"}`, 20, 51);
    
    // Contruir tabla de materias
    const tableData = notas.map(n => [
      n.observacion, // Nombre de la materia
      n.calificacion.toFixed(1),
      n.calificacion >= 3.0 ? "Aprobada" : "Reprobada"
    ]);
    
    autoTable(pdf, {
      startY: 60,
      head: [["Materia", "Calificación", "Estado"]],
      body: tableData,
      styles: { halign: "center" },
      headStyles: { fillColor: [22, 160, 133] }
    });
    
    // Pie de página
    const date = new Date().toLocaleDateString();
    pdf.setFontSize(10);
    pdf.text(`Generado el ${date}`, 20, pdf.internal.pageSize.height - 10);

    // Guardar PDF
    pdf.save(`Boletin_${nombre.replace(/\s+/g, "_")}.pdf`);
  });
  
  showSnackbar("Se han generado con éxito los boletines académicos.")
}

const fetchAll = async () => {
  Promise.all([
    axios.get(`${API_URL_ACTIVIDADES}/getall`),
    axios.get(`${API_URL_ESTUDIANTES}/getall`),
    axios.get(`${API_URL_PERIODOS}/getall`),
    axios.get(`${API_URL_NOTAS}/getall`),
    axios.get(`${API_URL_MATERIAS}/getall`),
    axios.get(`${API_URL_ESTUDIANTES_DETALLES}/getall`)
  ]).then(([actividadesRes, estudiantesRes, periodosRes, notasRes, materiasRes, estudiantesDetallesRes]) => {
    actividades.value = actividadesRes.data;
    estudiantes.value = estudiantesRes.data;
    periodos.value = periodosRes.data;
    materias.value = materiasRes.data;
    estudiantesDetalles.value = estudiantesDetallesRes.data;
    
    items.value = notasRes.data.map(item => {
      const actividad = actividades.value.find(a => a.idActividad === item.idActividad);
      const estudiante = estudiantes.value.find(e => e.idUsuario === item.idEstudiante);
      const periodo = periodos.value.find(p => p.idPeriodo === item.idPeriodo);
      return {
        ...item,
        nombreActividad: actividad ? actividad.nombre : '...',
        docEstudiante: estudiante ? estudiante.docIdentidad : '...',
        nombrePeriodo: periodo ? periodo.nombre : '...'
      }
    });
    
    if (userData.value.idRol === 3) { // Docente
      const materia = materias.value.find(item => item.idDocente === userData.value.idUsuario);
      const actividadesFiltradas = actividades.value.filter(item => item.idMateria === materia.idMateria);
      items.value = items.value.filter(item => 
        actividadesFiltradas.some(a => a.idActividad === item.idActividad)
      );
      
      actividades.value = actividades.value.filter(item => item.idMateria === materia.idMateria);
      
    } else if (userData.value.idRol === 4) { // Acudiente
      const estudiante = estudiantesDetalles.value.find(item => item.idAcudiente === userData.value.idUsuario);
      items.value = items.value.filter(item => item.idEstudiante === estudiante.idUsuario);
      
    } else if (userData.value.idRol === 5) { // Estudiante
      items.value = items.value.filter(item => item.idEstudiante === userData.value.idUsuario);
    }
    
    loading.value = false;
  });
};

onMounted(() => {
  fetchAll();
  const saved = localStorage.getItem('userData');
  if (saved) {
    userData.value = JSON.parse(saved);
  }
});
</script>
