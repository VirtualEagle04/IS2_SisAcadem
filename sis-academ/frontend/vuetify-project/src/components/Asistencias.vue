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
          <v-icon start>mdi-calendar-clock</v-icon>
          Gestión de Asistencias
        </v-toolbar-title>
        <v-text-field
          v-model="search"
          label="Buscar"
          prepend-inner-icon="mdi-magnify"
          variant="outlined"
          hide-details
          single-line
        ></v-text-field>
        <v-btn v-if="!soloLectura" class="me-2" prepend-icon="mdi-plus" color="green" @click="add">
          Agregar una Asistencia
        </v-btn>
        <v-btn
          v-if="!soloLectura"
          class="me-2"
          prepend-icon="mdi-delete"
          color="red"
          @click="handleConfirm"
          :disabled="selected.length === 0"
        >
          Eliminar Asistencias
        </v-btn>
      </v-toolbar>
    </template>
    
    <template v-slot:item.estado="{ item }">
      <v-chip v-if="item.estado ==='Presente'" color="green">{{ item.estado }}</v-chip>
      <v-chip v-if="item.estado ==='Ausente'" color="red">{{ item.estado }}</v-chip>
      <v-chip v-if="item.estado ==='Excusado'" color="grey-darken-2">{{ item.estado }}</v-chip>
      <v-chip v-if="item.estado ==='Tarde'" color="orange">{{ item.estado }}</v-chip>
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
          @click="handleConfirm(item.idAsistencia)"
        ></v-icon>
      </div>
    </template>
  </v-data-table>

  <v-dialog v-model="dialog" max-width="500">
    <v-card class="pb-5 pl-5 pr-5" :title="`${editing ? 'Editar' : 'Agregar'} una Asistencia`">
      
      <v-form @submit.prevent="handleSubmit">

        <v-select
          v-model="record.idEstudiante"
          label="Estudiante"
          :items="estudiantesSelect"
          item-title="nombreEstudiante"
          item-value="idUsuario"
        ></v-select>
        <v-select
          v-model="record.idClase"
          label="Horario de Clase"
          :items="horariosClases"
          item-title="nombreClase"
          item-value="idHorario"
        ></v-select>
        <v-select
          v-model="record.estado"
          label="Estado"
          :items="estados"
          item-title="value"
          item-value="value"
        ></v-select>
        <v-text-field
          v-model="record.observacion"
          label="Observaciones"
        ></v-text-field>
        
        <v-btn class="mt-2" @click="dialog = false" block>Cancelar</v-btn>
        <v-btn class="mt-2" type="submit" block color="green">Aceptar</v-btn>
      </v-form>
      
    </v-card>
  </v-dialog>
  
  <v-dialog v-model="confirmDialog.enabled" max-width="500">
    <v-card :title="`¿Eliminar ${selected.length === 0 ? 'la Asistencia' : 'todas las Asistencias'}?`" 
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

const userData = ref(null);
const soloLectura = computed(() => {
  if (!userData.value) return false;
  return userData.value.permisos?.soloLectura || false;
});

const estudiantesSelect = ref([]);

const API_URL_ESTUDIANTES = "http://localhost:8080/api/usuarios/usuarios";
const estudiantes = ref([]);

const API_URL_HORARIOS_CLASE = "http://localhost:8080/api/horarios/horarios-clases";
const horariosClases = ref([]);

const API_URL_MATERIAS = "http://localhost:8080/api/materias";
const materias = ref([]);

const API_URL_ASISTENCIAS = "http://localhost:8080/api/asistencias"
const items = ref([]);
const loading = ref(true);
const selected = ref([]);
const headers = [
  { title: "ID", key: "idAsistencia" },
  // { title: "ID Estudiante", key: "idEstudiante" },
  { title: "Estudiante", key: "docEstudiante" },
  // { title: "ID Clase", key: "idClase" },
  { title: "Clase", key: "nombreClase" },
  { title: "Estado", key: "estado" },
  { title: "Observaciones", key: "observacion" },
  { title: "Acciones", key: "acciones" },
];
const FORM_DATA = {
  idAsistencia: null,
  idEstudiante: null,
  idClase: null,
  tipo: "",
  observacion: null
};
const search = ref('');
const estados = ref([
  {value: 'Presente'},
  {value: 'Ausente'},
  {value: 'Tarde'},
  {value: 'Excusado'}
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
    .delete(`${API_URL_ASISTENCIAS}/delete/${id}`)
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
      selected.value.map((asistencia) =>
        axios.delete(`${API_URL_ASISTENCIAS}/delete/${asistencia.idAsistencia}`)
      )
    );
    showSnackbar("Asistencias eliminadas correctamente");
    selected.value = [];
  } catch (error) {
    showSnackbar("Error al eliminar las asistencias", "error");
  }
  fetchAll();
  confirmDialog.value.enabled = false;
  confirmDialog.value.id = null;
}

async function save() {
  if (editing.value) {
    await axios
      .put(`${API_URL_ASISTENCIAS}/update/${record.value.idAsistencia}`, record.value)
      .then((res) => {
        showSnackbar(res.data);
        dialog.value = false;
      })
      .catch((error) => {
        showSnackbar(error.response.data, "error");
      });
  } else {
    await axios
      .post(`${API_URL_ASISTENCIAS}/create`, record.value)
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
  if (e.target[0].value === "") valid.value = false;
  if (e.target[1].value === "") valid.value = false;
  if (e.target[2].value === "") valid.value = false;
  
  if (valid.value) {
    save();
  } else {
    showSnackbar("Campos incompletos", "error");
  }
  
  valid.value = true;
}

const fetchAll = async () => {
  Promise.all([
    axios.get(`${API_URL_ESTUDIANTES}/getall`),
    axios.get(`${API_URL_MATERIAS}/getall`),
    axios.get(`${API_URL_HORARIOS_CLASE}/getall`),
    axios.get(`${API_URL_ASISTENCIAS}/getall`)
  ]).then(([estudiantesRes, materiasRes, horariosRes, asistenciasRes]) => {
    estudiantes.value = estudiantesRes.data;
    materias.value = materiasRes.data;
    const clases = ref([]);
    clases.value = horariosRes.data;
    
    horariosClases.value = horariosRes.data.map(item => {
      const nombreMateria = materias.value.find(m => m.idMateria === item.idMateria);
      return {
        ...item,
        nombreClase: item ? (nombreMateria.nombre + " - " + item.diaSemana + " - " + item.horaInicio) : '...'
      }
    });
    
    items.value = asistenciasRes.data.map(item => {
      const estudiante = estudiantes.value.find(e => e.idUsuario === item.idEstudiante);
      const clase = horariosClases.value.find(c => c.idHorario === item.idClase);
      const nombreMateria = materias.value.find(m => m.idMateria === clase.idMateria);
      return {
        ...item,
        docEstudiante: estudiante ? estudiante.docIdentidad + " - " + estudiante.nombres + " " + estudiante.apellidos : '...',
        nombreClase: clase ? (nombreMateria.nombre + " - " + clase.diaSemana + " - " + clase.horaInicio) : '...'
      }
    });
    
    estudiantesSelect.value = estudiantes.value.filter(e => e.idRol === 5);
    estudiantesSelect.value = estudiantesSelect.value.map(item => {
      return {
        ...item,
        nombreEstudiante: item.docIdentidad + " - " + item.nombres + " " + item.apellidos
      }
    });
    
    if (userData.value.idRol === 3) {
      const materia = materias.value.find(item => item.idDocente === userData.value.idUsuario);
      horariosClases.value = horariosClases.value.filter(item => item.idMateria === materia.idMateria);
      
      const clasesFiltradas = clases.value.filter(item => item.idMateria === materia.idMateria);
      console.log(clasesFiltradas);
      items.value = items.value.filter(item => 
        clasesFiltradas.some(c => c.idHorario === item.idClase)
      );
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
