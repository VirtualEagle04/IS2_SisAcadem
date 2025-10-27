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
          <v-icon start>mdi-account-cog</v-icon>
          Gestión de Roles
        </v-toolbar-title>
        <v-btn class="me-2" prepend-icon="mdi-plus" color="green" @click="add">
          Agregar un Rol
        </v-btn>
        <v-btn
          class="me-2"
          prepend-icon="mdi-delete"
          color="red"
          @click="removeSelected"
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
          color="blue"
          icon="mdi-pencil"
          size="small"
          @click="edit(item)"
        ></v-icon>

        <v-icon
          color="red"
          icon="mdi-delete"
          size="small"
          @click="remove(item.idRol)"
        ></v-icon>
      </div>
    </template>
  </v-data-table>

  <v-dialog v-model="dialog" max-width="500">
    <v-card :title="`${editing ? 'Editar' : 'Agregar'} un Rol`">
      <template v-slot:text>
        <v-row>
          <v-col cols="12">
            <v-text-field
              v-model="record.nombre"
              label="Nombre del Rol"
            ></v-text-field>
          </v-col>
          <v-col cols="12">
            <v-checkbox v-model="record.soloLectura" label="Solo Lectura"></v-checkbox>
          </v-col>
          <v-col cols="12">
            <v-checkbox v-model="record.rol" label="Roles"></v-checkbox>
          </v-col>
          <v-col cols="12">
            <v-checkbox v-model="record.usuario" label="Usuarios"></v-checkbox>
          </v-col>
          <v-col cols="12">
            <v-checkbox v-model="record.grado" label="Grados"></v-checkbox>
          </v-col>
          <v-col cols="12">
            <v-checkbox v-model="record.curso" label="Cursos"></v-checkbox>
          </v-col>
          <v-col cols="12">
            <v-checkbox v-model="record.materia" label="Materias"></v-checkbox>
          </v-col>
          <v-col cols="12">
            <v-checkbox
              v-model="record.actividad"
              label="Actividades"
            ></v-checkbox>
          </v-col>
          <v-col cols="12">
            <v-checkbox v-model="record.horario" label="Horarios"></v-checkbox>
          </v-col>
          <v-col cols="12">
            <v-checkbox v-model="record.nota" label="Notas"></v-checkbox>
          </v-col>
          <v-col cols="12">
            <v-checkbox
              v-model="record.asistencia"
              label="Asistencia"
            ></v-checkbox>
          </v-col>
          <v-col cols="12">
            <v-checkbox
              v-model="record.matricula"
              label="Matricula"
            ></v-checkbox>
          </v-col>
        </v-row>
      </template>

      <v-divider></v-divider>

      <v-card-actions class="bg-surface-light">
        <v-btn text="Cancelar" variant="plain" @click="dialog = false"></v-btn>
        <v-spacer></v-spacer>
        <v-btn text="Guardar" @click="save"></v-btn>
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

const API_URL = "http://localhost:8080/api/roles";
const items = ref([]);
const loading = ref(true);
const selected = ref([]);
const headers = [
  { title: "ID", key: "idRol" },
  { title: "Nombre", key: "nombre" },
  { title: "Solo Lectura", key: "soloLectura" },
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

const snackbar = ref(false);
const snackbarColor = ref("success");
const snackbarMsg = ref("");

function showSnackbar(msg, color = "success") {
  snackbarMsg.value = msg;
  snackbarColor.value = color;
  snackbar.value = true;
}

const record = ref(FORM_DATA);
const dialog = shallowRef(false);
const editing = shallowRef(false);

function add() {
  editing.value = false;
  record.value = { ...FORM_DATA };
  dialog.value = true;
}

function edit(item) {
  editing.value = true;
  record.value = {...item};
  dialog.value = true;
}

async function remove(id) {
  if (!confirm("¿Está seguro de eliminar el Rol?")) return;

  await axios
    .delete(`${API_URL}/delete/${id}`)
    .then((res) => {
      showSnackbar(res.data);
    })
    .catch((error) => {
      showSnackbar(error.response.data, "error");
    });
  fetch();
}

async function removeSelected() {
  if (selected.length === 0) return;
  if (!confirm("¿Está seguro de eliminar los Roles seleccionados?")) return;

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
}

async function save() {
  if (editing.value) {
    await axios
      .put(`${API_URL}/update/${record.value.idRol}`, record.value)
      .then((res) => {
        showSnackbar(res.data);
      })
      .catch((error) => {
        showSnackbar(error.response.data, "error");
      });
  } else {
    await axios
      .post(`${API_URL}/create`, record.value)
      .then((res) => {
        showSnackbar(res.data);
      })
      .catch((error) => {
        showSnackbar(error.response.data, "error");
      });
  }
	fetch();
	editing.value = false;
	dialog.value = false;
}

const fetch = async () => {
  await axios.get(`${API_URL}/getall`).then((res) => {
    items.value = res.data;
    loading.value = false;
  });
};

onMounted(() => {
  fetch();
});
</script>
