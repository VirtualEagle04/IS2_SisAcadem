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
  >
    <template v-slot:top>
      <v-toolbar flat class="rounded">
        <v-toolbar-title>
          <v-icon start>mdi-file-sign</v-icon>
          Gestión de Matrículas
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
          Agregar una Matrícula
        </v-btn>
        <v-btn
          v-if="!soloLectura"
          class="me-2"
          prepend-icon="mdi-delete"
          color="red"
          @click="handleConfirm"
          :disabled="selected.length === 0"
        >
          Eliminar Matrículas
        </v-btn>
      </v-toolbar>
    </template>
    
    <template v-slot:item.estado="{ item }">
      <v-chip v-if="item.estado ==='Activa'" color="green">{{ item.estado }}</v-chip>
      <v-chip v-if="item.estado ==='Inactiva'" color="red">{{ item.estado }}</v-chip>
      <v-chip v-if="item.estado ==='Retirado'" color="grey-darken-2">{{ item.estado }}</v-chip>
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
          @click="handleConfirm(item.idMatricula)"
        ></v-icon>
      </div>
    </template>
  </v-data-table>

  <v-dialog v-model="dialog" max-width="500">
    <v-card class="pb-5 pl-5 pr-5" :title="`${editing ? 'Editar' : 'Agregar'} una Matricula`">
      
      <v-form @submit.prevent="handleSubmit">

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
        <v-text-field
          v-model="record.fechaMatricula"
          label="Fecha de la Matricula"
          :rules="[rules.required]"
          placeholder="YYYY-MM-DD"
        ></v-text-field>
        <v-select
          v-model="record.estado"
          label="Estado"
          :items="estados"
          item-title="value"
          item-value="value"
        ></v-select>
        
        <v-btn class="mt-2" @click="dialog = false" block>Cancelar</v-btn>
        <v-btn class="mt-2" type="submit" block color="green">Aceptar</v-btn>
      </v-form>
      
    </v-card>
  </v-dialog>
  
  <v-dialog v-model="confirmDialog.enabled" max-width="500">
    <v-card :title="`¿Eliminar ${selected.length === 0 ? 'la Matrícula' : 'todas las Matrículas'}?`" 
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

const API_URL_ESTUDIANTES = "http://localhost:8080/api/usuarios/usuarios";
const estudiantes = ref([]);

const API_URL_PERIODOS = "http://localhost:8080/api/horarios/periodos-academicos";
const periodos = ref([]);

const API_URL_MATRICULAS = "http://localhost:8080/api/matriculas"
const items = ref([]);
const loading = ref(true);
const selected = ref([]);
const headers = [
  { title: "ID", key: "idMatricula" },
  // { title: "ID Estudiante", key: "idEstudiante" },
  { title: "Estudiante", key: "docEstudiante" },
  // { title: "ID Periodo", key: "idPeriodo" },
  { title: "Periodo", key: "nombrePeriodo" },
  { title: "Estado", key: "estado" },
  { title: "Acciones", key: "acciones" },
];
const FORM_DATA = {
  idMatricula: null,
  idEstudiante: null,
  idPeriodo: null,
  estado: ""
};
const search = ref('');
const estados = ref([
  {value: 'Activa'},
  {value: 'Inactiva'},
  {value: 'Retirado'}
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
    .delete(`${API_URL_MATRICULAS}/delete/${id}`)
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
      selected.value.map((matricula) =>
        axios.delete(`${API_URL_MATRICULAS}/delete/${matricula.idMatricula}`)
      )
    );
    showSnackbar("Matrpiculas eliminadas correctamente");
    selected.value = [];
  } catch (error) {
    showSnackbar("Error al eliminar las matrículas", "error");
  }
  fetchAll();
  confirmDialog.value.enabled = false;
  confirmDialog.value.id = null;
}

async function save() {
  if (editing.value) {
    await axios
      .put(`${API_URL_MATRICULAS}/update/${record.value.idMatricula}`, record.value)
      .then((res) => {
        showSnackbar(res.data);
        dialog.value = false;
      })
      .catch((error) => {
        showSnackbar(error.response.data, "error");
      });
  } else {
    await axios
      .post(`${API_URL_MATRICULAS}/create`, record.value)
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

const fetchAll = async () => {
  Promise.all([
    axios.get(`${API_URL_ESTUDIANTES}/getall`),
    axios.get(`${API_URL_PERIODOS}/getall`),
    axios.get(`${API_URL_MATRICULAS}/getall`)
  ]).then(([estudiantesRes, periodosRes, matriculasRes]) => {
    estudiantes.value = estudiantesRes.data;
    periodos.value = periodosRes.data;
    
    items.value = matriculasRes.data.map(item => {
      const estudiante = estudiantes.value.find(e => e.idUsuario === item.idEstudiante);
      const periodo = periodos.value.find(p => p.idPeriodo === item.idPeriodo);
      return {
        ...item,
        docEstudiante: estudiante ? estudiante.docIdentidad : '...',
        nombrePeriodo: periodo ? periodo.nombre : '...'
      }
    });
    
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
