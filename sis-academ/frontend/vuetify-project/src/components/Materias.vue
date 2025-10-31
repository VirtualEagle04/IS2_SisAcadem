<template>
  <v-data-table
    :headers="headers"
    :items="items"
    :loading="loading"
    density="comfortable"
    return-object
    v-model="selected"
    show-select
  >
    <template v-slot:top>
      <v-toolbar flat class="rounded">
        <v-toolbar-title>
          <v-icon start>mdi-book-multiple</v-icon>
          Gestión de Materias
        </v-toolbar-title>
        <v-btn class="me-2" prepend-icon="mdi-plus" color="green" @click="add">
          Agregar una Materia
        </v-btn>
        <v-btn
          class="me-2"
          prepend-icon="mdi-delete"
          color="red"
          @click="handleConfirm"
          :disabled="selected.length === 0"
        >
          Eliminar Materias
        </v-btn>
      </v-toolbar>
    </template>
    
    <template v-slot:item.idDocente="{ item }">
      {{ usuarios.find(doc => doc.idUsuario === item.idDocente)?.nombres || 'Sin docente' }}
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
          @click="handleConfirm(item.idMateria)"
        ></v-icon>
      </div>
    </template>
  </v-data-table>

  <v-dialog v-model="dialog" max-width="500">
    <v-card class="pb-5 pl-5 pr-5" :title="`${editing ? 'Editar' : 'Agregar'} una Materia`">
      
      <v-form @submit.prevent="handleSubmit">
        
        <v-select
          v-model="record.idDocente"
          label="Docente"
          :items="usuarios.filter(u => u.idRol === 3)"
          item-title="docIdentidad"
          item-value="idUsuario"
        ></v-select>
        <v-text-field
          v-model="record.nombre"
          label="Nombre de la Materia"
          :rules="[rules.required]"
        ></v-text-field>
        <v-text-field
          v-model="record.descripcion"
          label="Descripción de la Materia"
          :rules="[rules.required]"
        ></v-text-field>
        
        <v-btn class="mt-2" @click="dialog = false" block>Cancelar</v-btn>
        <v-btn class="mt-2" type="submit" block color="green">Aceptar</v-btn>
      </v-form>
      
    </v-card>
  </v-dialog>
  
  <v-dialog v-model="confirmDialog.enabled" max-width="500">
    <v-card :title="`¿Eliminar ${selected.length === 0 ? 'la Materia' : 'todas las Materias'}?`" 
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

const API_URL_USUARIOS = "http://localhost:8080/api/usuarios/usuarios";
const usuarios = ref([]);

const API_URL_MATERIAS = "http://localhost:8080/api/materias";
const items = ref([]);
const loading = ref(true);
const selected = ref([]);
const headers = [
  { title: "ID", key: "idMateria" },
  { title: "Docente", key: "idDocente" },
  { title: "Nombre", key: "nombre" },
  { title: "Descripción", key: "descripcion" },
  { title: "Acciones", key: "acciones" },
];
const FORM_DATA = {
  idMateria: null,
  idDocente: null,
  nombre: "",
  descripcion: ""
};

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
    .delete(`${API_URL_MATERIAS}/delete/${id}`)
    .then((res) => {
      showSnackbar(res.data);
    })
    .catch((error) => {
      showSnackbar(error.response.data, "error");
    });
  fetch();
  confirmDialog.value.enabled = false;
  confirmDialog.value.id = null;
}

async function removeSelected() {
  if (selected.length === 0) return;

  try {
    await Promise.all(
      selected.value.map((materia) =>
        axios.delete(`${API_URL_MATERIAS}/delete/${materia.idMateria}`)
      )
    );
    showSnackbar("Materias eliminadas correctamente");
    selected.value = [];
  } catch (error) {
    showSnackbar("Error al eliminar las materias", "error");
  }
  fetch();
  confirmDialog.value.enabled = false;
  confirmDialog.value.id = null;
}

async function save() {
  if (editing.value) {
    await axios
      .put(`${API_URL_MATERIAS}/update/${record.value.idMateria}`, record.value)
      .then((res) => {
        showSnackbar(res.data);
        dialog.value = false;
      })
      .catch((error) => {
        showSnackbar(error.response.data, "error");
      });
  } else {
    await axios
      .post(`${API_URL_MATERIAS}/create`, record.value)
      .then((res) => {
        showSnackbar(res.data);
        dialog.value = false;
      })
      .catch((error) => {
        showSnackbar(error.response.data, "error");
      });
  }
  fetch();
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

const fetch = async () => {
  await axios.get(`${API_URL_MATERIAS}/getall`).then((res) => {
    items.value = res.data;
    loading.value = false;
  });
};

const fetchUsuarios = async () => {
  await axios.get(`${API_URL_USUARIOS}/getall`).then((res) => {
    usuarios.value = res.data;
    loading.value = false;
  });
}

onMounted(() => {
  fetch();
  fetchUsuarios();
});
</script>
