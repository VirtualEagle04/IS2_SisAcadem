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
          <v-icon start>mdi-google-classroom</v-icon>
          Gestión de Horarios
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
          Agregar un Horario
        </v-btn>
        <v-btn
          v-if="!soloLectura"
          class="me-2"
          prepend-icon="mdi-delete"
          color="red"
          @click="handleConfirm"
          :disabled="selected.length === 0"
        >
          Eliminar Horarios
        </v-btn>
      </v-toolbar>
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
          @click="handleConfirm(item.idHorario)"
        ></v-icon>
      </div>
    </template>
  </v-data-table>

  <v-dialog v-model="dialog" max-width="500">
    <v-card class="pb-5 pl-5 pr-5" :title="`${editing ? 'Editar' : 'Agregar'} un Curso`">
      
      <v-form @submit.prevent="handleSubmit">
        
        <v-select
          v-model="record.idGrado"
          label="Grado"
          :items="grados"
          item-title="nombre"
          item-value="idGrado"
        ></v-select>
        <v-select
          v-model="record.idMateria"
          label="Materia"
          :items="materias"
          item-title="nombre"
          item-value="idMateria"
        ></v-select>
        <v-select
          v-model="record.diaSemana"
          label="Día de la Semana"
          :items="dias"
          item-title="value"
          item-value="value"
        ></v-select>
        <v-text-field
          v-model="record.horaInicio"
          label="Hora de Inicio"
          :rules="[rules.required]"
          placeholder="HH:MM:SS"
        ></v-text-field>
        <v-text-field
          v-model="record.horaFin"
          label="Hora de Fin"
          :rules="[rules.required]"
          placeholder="HH:MM:SS"
        ></v-text-field>
        <v-text-field
          v-model="record.salon"
          label="Salón"
          :rules="[rules.required]"
        ></v-text-field>
        
        <v-btn class="mt-2" @click="dialog = false" block>Cancelar</v-btn>
        <v-btn class="mt-2" type="submit" block color="green">Aceptar</v-btn>
      </v-form>
      
    </v-card>
  </v-dialog>
  
  <v-dialog v-model="confirmDialog.enabled" max-width="500">
    <v-card :title="`¿Eliminar ${selected.length === 0 ? 'el Horario' : 'todos los Horarios'}?`" 
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

const API_URL_GRADOS = "http://localhost:8080/api/grados";
const grados = ref([]);

const API_URL_MATERIAS = "http://localhost:8080/api/materias";
const materias = ref([]);

const API_URL_HORARIOS = "http://localhost:8080/api/horarios/horarios-clases";
const items = ref([]);
const loading = ref(true);
const selected = ref([]);
const headers = [
  { title: "ID", key: "idHorario" },
  // { title: "ID Grado", key: "idGrado" },
  { title: "Grado", key: "nombreGrado" },
  // { title: "ID Materia", key: "idMateria" },
  { title: "Materia", key: "nombreMateria" },
  { title: "Día de la Semana", key: "diaSemana" },
  { title: "Hora de Inicio", key: "horaInicio" },
  { title: "Hora de Fin", key: "horaFin" },
  { title: "Salón", key: "salon" },
  { title: "Acciones", key: "acciones" },
];
const FORM_DATA = {
  idHoraio: null,
  idGrado: null,
  idMateria: null,
  diaSemana: "",
  horaInicio: "",
  horaFin: "",
  salon: ""
};
const search = ref('');
const dias = ref([
  {value: 'Lunes'},
  {value: 'Martes'},
  {value: 'Miércoles'},
  {value: 'Jueves'},
  {value: 'Viernes'}
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
    .delete(`${API_URL_HORARIOS}/delete/${id}`)
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
      selected.value.map((horario) =>
        axios.delete(`${API_URL_HORARIOS}/delete/${horario.idHorario}`)
      )
    );
    showSnackbar("Horarios eliminados correctamente");
    selected.value = [];
  } catch (error) {
    showSnackbar("Error al eliminar los horarios", "error");
  }
  fetchAll();
  confirmDialog.value.enabled = false;
  confirmDialog.value.id = null;
}

async function save() {
  if (editing.value) {
    await axios
      .put(`${API_URL_HORARIOS}/update/${record.value.idHorario}`, record.value)
      .then((res) => {
        showSnackbar(res.data);
        dialog.value = false;
      })
      .catch((error) => {
        showSnackbar(error.response.data, "error");
      });
  } else {
    await axios
      .post(`${API_URL_HORARIOS}/create`, record.value)
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
  if (e.target[3].value === "") valid.value = false;
  if (e.target[4].value === "") valid.value = false;
  if (e.target[5].value === "") valid.value = false;
  
  if (valid.value) {
    save();
  } else {
    showSnackbar("Campos incompletos", "error");
  }
  
  valid.value = true;
}

const fetchAll = async () => {
  Promise.all([
    axios.get(`${API_URL_GRADOS}/getall`),
    axios.get(`${API_URL_MATERIAS}/getall`),
    axios.get(`${API_URL_HORARIOS}/getall`)
  ]).then(([gradosRes, materiasRes, horariosRes]) => {
    grados.value = gradosRes.data;
    materias.value = materiasRes.data;
    
    items.value = horariosRes.data.map(item => {
      const grado = grados.value.find(g => g.idGrado === item.idGrado);
      const materia = materias.value.find(m => m.idMateria === item.idMateria);
      return {
        ...item,
        nombreGrado: grado ? grado.nombre : '...',
        nombreMateria: materia ? materia.nombre : '...',
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
