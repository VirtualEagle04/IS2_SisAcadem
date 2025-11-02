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
  >
    <template v-slot:top>
      <v-toolbar flat class="rounded">
        <v-toolbar-title>
          <v-icon start>mdi-account-group</v-icon>
          Gestión de Cursos
        </v-toolbar-title>
        <v-text-field
          v-model="search"
          label="Buscar"
          prepend-inner-icon="mdi-magnify"
          variant="outlined"
          hide-details
          single-line
        ></v-text-field>
        <v-btn class="me-2" prepend-icon="mdi-plus" color="green" @click="add">
          Agregar un Curso
        </v-btn>
        <v-btn
          class="me-2"
          prepend-icon="mdi-delete"
          color="red"
          @click="handleConfirm"
          :disabled="selected.length === 0"
        >
          Eliminar Cursos
        </v-btn>
      </v-toolbar>
    </template>

    <template v-slot:item.acciones="{ item }">
      <div class="d-flex ga-2">
        <v-icon
          color="blue"
          icon="mdi-pencil"
          size="small"
          @click="edit(item)"
        ></v-icon>

        <v-icon
          color="red"
          icon="mdi-delete"
          size="small"
          @click="handleConfirm(item.idCurso)"
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
        <v-text-field
          v-model="record.nombre"
          label="Nombre del Curso"
          :rules="[rules.required]"
        ></v-text-field>
        
        <v-btn class="mt-2" @click="dialog = false" block>Cancelar</v-btn>
        <v-btn class="mt-2" type="submit" block color="green">Aceptar</v-btn>
      </v-form>
      
    </v-card>
  </v-dialog>
  
  <v-dialog v-model="confirmDialog.enabled" max-width="500">
    <v-card :title="`¿Eliminar ${selected.length === 0 ? 'el Curso' : 'todos los Cursos'}?`" 
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
import { ref, onMounted, shallowRef } from "vue";
import axios from "axios";

const API_URL_GRADOS = "http://localhost:8080/api/grados";
const grados = ref([]);

const API_URL_CURSOS = "http://localhost:8080/api/cursos";
const items = ref([]);
const loading = ref(true);
const selected = ref([]);
const headers = [
  { title: "ID", key: "idCurso" },
  // { title: "ID Grado", key: "idGrado" },
  { title: "Grado", key: "nombreGrado" },
  { title: "Nombre", key: "nombre" },
  { title: "Acciones", key: "acciones" },
];
const FORM_DATA = {
  idCurso: null,
  idGrado: null,
  nombre: "",
};
const search = ref('');

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
    .delete(`${API_URL_CURSOS}/delete/${id}`)
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
      selected.value.map((curso) =>
        axios.delete(`${API_URL_CURSOS}/delete/${curso.idCurso}`)
      )
    );
    showSnackbar("Cursos eliminados correctamente");
    selected.value = [];
  } catch (error) {
    showSnackbar("Error al eliminar los cursos", "error");
  }
  fetchAll();
  confirmDialog.value.enabled = false;
  confirmDialog.value.id = null;
}

async function save() {
  if (editing.value) {
    await axios
      .put(`${API_URL_CURSOS}/update/${record.value.idCurso}`, record.value)
      .then((res) => {
        showSnackbar(res.data);
        dialog.value = false;
      })
      .catch((error) => {
        showSnackbar(error.response.data, "error");
      });
  } else {
    await axios
      .post(`${API_URL_CURSOS}/create`, record.value)
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
    axios.get(`${API_URL_CURSOS}/getall`)
  ]).then(([gradosRes, cursosRes]) => {
    grados.value = gradosRes.data;
    
    items.value = cursosRes.data.map(item => {
      const grado = grados.value.find(g => g.idGrado === item.idGrado);
      return {
        ...item,
        nombreGrado: grado ? grado.nombre : '...'
      }
    });
    
    loading.value = false;
  });
};

onMounted(() => {
  fetchAll();
});
</script>
