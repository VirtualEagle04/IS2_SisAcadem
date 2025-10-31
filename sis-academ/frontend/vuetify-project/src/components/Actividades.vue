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
          <v-icon start>mdi-format-list-checkbox</v-icon>
          Gestión de Actividades
        </v-toolbar-title>
        <v-btn class="me-2" prepend-icon="mdi-plus" color="green" @click="add">
          Agregar una Actividad
        </v-btn>
        <v-btn
          class="me-2"
          prepend-icon="mdi-delete"
          color="red"
          @click="handleConfirm"
          :disabled="selected.length === 0"
        >
          Eliminar Actividades
        </v-btn>
      </v-toolbar>
    </template>
    
    <template v-slot:item.idMateria="{ item }">
      {{ materias.find(mat => mat.idMateria === item.idMateria)?.nombre || 'Sin materia' }}
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
          @click="handleConfirm(item.idActividad)"
        ></v-icon>
      </div>
    </template>
  </v-data-table>

  <v-dialog v-model="dialog" max-width="500">
    <v-card class="pb-5 pl-5 pr-5" :title="`${editing ? 'Editar' : 'Agregar'} una Actividad`">
      
      <v-form @submit.prevent="handleSubmit">
        
        <v-select
          v-model="record.idMateria"
          label="Materia"
          :items="materias"
          item-title="nombre"
          item-value="idMateria"
        ></v-select>
        <v-text-field
          v-model="record.nombre"
          label="Nombre de la Actividad"
          :rules="[rules.required]"
        ></v-text-field>
        <v-text-field
          v-model="record.descripcion"
          label="Descripción de la Actividad"
          :rules="[rules.required]"
        ></v-text-field>
        <v-number-input
          v-model="record.porcentaje"
          label="Porcentaje"
          :rules="[rules.required]"
          :min="0"
          :max="100"
          suffix="%"
        ></v-number-input>
        
        <v-btn class="mt-2" @click="dialog = false" block>Cancelar</v-btn>
        <v-btn class="mt-2" type="submit" block color="green">Aceptar</v-btn>
      </v-form>
      
    </v-card>
  </v-dialog>
  
  <v-dialog v-model="confirmDialog.enabled" max-width="500">
    <v-card :title="`¿Eliminar ${selected.length === 0 ? 'la Actividad' : 'todas las Actividades'}?`" 
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

const API_URL_MATERIAS = "http://localhost:8080/api/materias";
const materias = ref([]);

const API_URL_ACTIVIDADES = "http://localhost:8080/api/actividades"
const items = ref([]);
const loading = ref(true);
const selected = ref([]);
const headers = [
  { title: "ID", key: "idActividad" },
  { title: "Materia", key: "idMateria" },
  { title: "Nombre", key: "nombre" },
  { title: "Descripción", key: "descripcion" },
  { title: "Porcentaje (%)", key: "porcentaje" },
  { title: "Acciones", key: "acciones" },
];
const FORM_DATA = {
  idActividad: null,
  idMateria: null,
  nombre: "",
  descripcion: "",
  porcentaje: null
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
      selected.value.map((actividad) =>
        axios.delete(`${API_URL_ACTIVIDADES}/delete/${actividad.idActividad}`)
      )
    );
    showSnackbar("Actividades eliminadas correctamente");
    selected.value = [];
  } catch (error) {
    showSnackbar("Error al eliminar las actividades", "error");
  }
  fetch();
  confirmDialog.value.enabled = false;
  confirmDialog.value.id = null;
}

async function save() {
  if (editing.value) {
    await axios
      .put(`${API_URL_ACTIVIDADES}/update/${record.value.idActividad}`, record.value)
      .then((res) => {
        showSnackbar(res.data);
        dialog.value = false;
      })
      .catch((error) => {
        showSnackbar(error.response.data, "error");
      });
  } else {
    await axios
      .post(`${API_URL_ACTIVIDADES}/create`, record.value)
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
  if (e.target[4].value === "") valid.value = false;
  
  if (valid.value) {
    save();
  } else {
    showSnackbar("Campos incompletos", "error");
  }
  
  valid.value = true;
}

const fetch = async () => {
  await axios.get(`${API_URL_ACTIVIDADES}/getall`).then((res) => {
    items.value = res.data;
    loading.value = false;
  });
};

const fetchMaterias = async () => {
  await axios.get(`${API_URL_MATERIAS}/getall`).then((res) => {
    materias.value = res.data;
    loading.value = false;
  });
}

onMounted(() => {
  fetch();
  fetchMaterias();
});
</script>
