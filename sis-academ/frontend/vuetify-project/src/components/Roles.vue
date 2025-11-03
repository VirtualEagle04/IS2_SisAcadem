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
          <v-icon start>mdi-account-cog</v-icon>
          Gestión de Roles
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
          Agregar un Rol
        </v-btn>
        <v-btn
          v-if="!soloLectura"
          class="me-2"
          prepend-icon="mdi-delete"
          color="red"
          @click="handleConfirm"
          :disabled="selected.length === 0"
        >
          Eliminar Roles
        </v-btn>
      </v-toolbar>
    </template>
    <template v-slot:item.soloLectura="{ item }">
      <v-icon
        :color="item.soloLectura ? 'green' : 'red'"
        :icon="item.soloLectura ? 'mdi-check' : 'mdi-close'"
        size="small"
      ></v-icon>
    </template>

    <template v-slot:item.rol="{ item }">
      <v-icon
        :color="item.rol ? 'green' : 'red'"
        :icon="item.rol ? 'mdi-check' : 'mdi-close'"
        size="small"
      ></v-icon>
    </template>

    <template v-slot:item.usuario="{ item }">
      <v-icon
        :color="item.usuario ? 'green' : 'red'"
        :icon="item.usuario ? 'mdi-check' : 'mdi-close'"
        size="small"
      ></v-icon>
    </template>

    <template v-slot:item.grado="{ item }">
      <v-icon
        :color="item.grado ? 'green' : 'red'"
        :icon="item.grado ? 'mdi-check' : 'mdi-close'"
        size="small"
      ></v-icon>
    </template>

    <template v-slot:item.curso="{ item }">
      <v-icon
        :color="item.curso ? 'green' : 'red'"
        :icon="item.curso ? 'mdi-check' : 'mdi-close'"
        size="small"
      ></v-icon>
    </template>

    <template v-slot:item.materia="{ item }">
      <v-icon
        :color="item.materia ? 'green' : 'red'"
        :icon="item.materia ? 'mdi-check' : 'mdi-close'"
        size="small"
      ></v-icon>
    </template>

    <template v-slot:item.actividad="{ item }">
      <v-icon
        :color="item.actividad ? 'green' : 'red'"
        :icon="item.actividad ? 'mdi-check' : 'mdi-close'"
        size="small"
      ></v-icon>
    </template>

    <template v-slot:item.horario="{ item }">
      <v-icon
        :color="item.horario ? 'green' : 'red'"
        :icon="item.horario ? 'mdi-check' : 'mdi-close'"
        size="small"
      ></v-icon>
    </template>

    <template v-slot:item.nota="{ item }">
      <v-icon
        :color="item.nota ? 'green' : 'red'"
        :icon="item.nota ? 'mdi-check' : 'mdi-close'"
        size="small"
      ></v-icon>
    </template>

    <template v-slot:item.asistencia="{ item }">
      <v-icon
        :color="item.asistencia ? 'green' : 'red'"
        :icon="item.asistencia ? 'mdi-check' : 'mdi-close'"
        size="small"
      ></v-icon>
    </template>

    <template v-slot:item.matricula="{ item }">
      <v-icon
        :color="item.matricula ? 'green' : 'red'"
        :icon="item.matricula ? 'mdi-check' : 'mdi-close'"
        size="small"
      ></v-icon>
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
          @click="handleConfirm(item.idRol)"
        ></v-icon>
      </div>
    </template>
  </v-data-table>

  <v-dialog v-model="dialog" max-width="500">
    <v-card class="pb-5 pl-5 pr-5" :title="`${editing ? 'Editar' : 'Agregar'} un Rol`">
      
      <v-form @submit.prevent="handleSubmit">
        <v-text-field
          v-model="record.nombre"
          label="Nombre del Rol"
          :rules="[required]"
        ></v-text-field>
        <v-switch color="light-green" v-model="record.soloLectura" label="Read-Only"></v-switch>
        <v-switch color="light-green" v-model="record.rol" label="Roles"></v-switch>
        <v-switch color="light-green" v-model="record.usuario" label="Usuarios"></v-switch>
        <v-switch color="light-green" v-model="record.grado" label="Grados"></v-switch>
        <v-switch color="light-green" v-model="record.curso" label="Cursos"></v-switch>
        <v-switch color="light-green" v-model="record.materia" label="Materias"></v-switch>
        <v-switch color="light-green" v-model="record.actividad" label="Actividades"></v-switch>
        <v-switch color="light-green" v-model="record.horario" label="Horarios"></v-switch>
        <v-switch color="light-green" v-model="record.nota" label="Notas"></v-switch>
        <v-switch color="light-green" v-model="record.asistencia" label="Asistencia"></v-switch>
        <v-switch color="light-green" v-model="record.matricula" label="Matricula"></v-switch>
        
        <v-btn class="mt-2" @click="dialog = false" block>Cancelar</v-btn>
        <v-btn class="mt-2" type="submit" block color="green">Aceptar</v-btn>
      </v-form>
      
    </v-card>
  </v-dialog>
  
  <v-dialog v-model="confirmDialog.enabled" max-width="500">
    <v-card :title="`¿Eliminar ${selected.length === 0 ? 'el Rol' : 'todos los Roles'}?`" 
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

const API_URL = "http://localhost:8080/api/roles";
const items = ref([]);
const loading = ref(true);
const selected = ref([]);
const headers = [
  { title: "ID", key: "idRol" },
  { title: "Nombre", key: "nombre" },
  { title: "Read-Only", key: "soloLectura" },
  { title: "Rol", key: "rol" },
  { title: "Usuario", key: "usuario" },
  { title: "Grado", key: "grado" },
  { title: "Curso", key: "curso" },
  { title: "Materia", key: "materia" },
  { title: "Actividad", key: "actividad" },
  { title: "Horario", key: "horario" },
  { title: "Nota", key: "nota" },
  { title: "Asistencia", key: "asistencia" },
  { title: "Matricula", key: "matricula" },
  { title: "Acciones", key: "acciones" },
];
const FORM_DATA = {
  idRol: null,
  nombre: "",
  soloLectura: false,
  rol: false,
  usuario: false,
  grado: false,
  curso: false,
  materia: false,
  actividad: false,
  horario: false,
  nota: false,
  asistencia: false,
  matricula: false,
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

function required(value) {
  return value ? true : 'Campo requerido.';
}

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
    .delete(`${API_URL}/delete/${id}`)
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
      selected.value.map((rol) =>
        axios.delete(`${API_URL}/delete/${rol.idRol}`)
      )
    );
    showSnackbar("Roles eliminados correctamente");
    selected.value = [];
  } catch (error) {
    showSnackbar("Error al eliminar los roles", "error");
  }
  fetch();
  confirmDialog.value.enabled = false;
  confirmDialog.value.id = null;
}

async function save() {
  if (editing.value) {
    await axios
      .put(`${API_URL}/update/${record.value.idRol}`, record.value)
      .then((res) => {
        showSnackbar(res.data);
        dialog.value = false;
      })
      .catch((error) => {
        showSnackbar(error.response.data, "error");
      });
  } else {
    await axios
      .post(`${API_URL}/create`, record.value)
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
  if (e.target[0].value === "") valid.value = false;
  
  if (valid.value) {
    save();
  } else {
    showSnackbar("Campos incompletos", "error");
  }
  
  valid.value = true;
}

const fetch = async () => {
  await axios.get(`${API_URL}/getall`).then((res) => {
    items.value = res.data;
    loading.value = false;
  });
};

onMounted(() => {
  fetch();
  const saved = localStorage.getItem('userData');
  if (saved) {
    userData.value = JSON.parse(saved);
  }
});
</script>
