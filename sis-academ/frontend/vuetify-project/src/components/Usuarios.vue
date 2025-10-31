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
          <v-icon start>mdi-account-school</v-icon>
          Gestión de Usuarios
        </v-toolbar-title>
        <v-btn class="me-2" prepend-icon="mdi-plus" color="green" @click="add">
          Agregar un Usuario
        </v-btn>
        <v-btn
          class="me-2"
          prepend-icon="mdi-delete"
          color="red"
          @click="handleConfirm"
          :disabled="selected.length === 0"
        >
          Eliminar Usuarios
        </v-btn>
      </v-toolbar>
    </template>
    
    <template v-slot:item.idRol="{ item }">
      {{ roles.find(rol => rol.idRol === item.idRol)?.nombre || 'Sin rol' }}
    </template>
    
    <template v-slot:item.clave="{ item }">
      -
    </template>
    
    <template v-slot:item.activo="{ item }">
      {{ item.activo ? 'Sí' : 'No' }}
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
          @click="handleConfirm(item.idUsuario)"
        ></v-icon>
      </div>
    </template>
  </v-data-table>

  <v-dialog v-model="dialog" max-width="500">
    <v-card class="pb-5 pl-5 pr-5" :title="`${editing ? 'Editar' : 'Agregar'} un Usuario`">
      
      <v-form @submit.prevent="handleSubmit">
        <v-select
          v-model="record.idRol"
          label="Rol"
          :items="roles"
          item-title="nombre"
          item-value="idRol"
        ></v-select>
        <v-text-field
          v-model="record.usuario"
          label="Nombre del Usuario"
          :rules="[rules.required]"
        ></v-text-field>
        <v-text-field
          v-model="record.clave"
          label="Contraseña"
          :rules="[rules.required]"
          :append-inner-icon="passVisible ? 'mdi-eye-off' : 'mdi-eye'"
          :type="passVisible ? 'text' : 'password'"
          @click:append-inner="passVisible = !passVisible"
        ></v-text-field>
        <v-text-field
          v-model="record.nombres"
          label="Nombres"
          :rules="[rules.required]"
        ></v-text-field>
        <v-text-field
          v-model="record.apellidos"
          label="Apellidos"
          :rules="[rules.required]"
        ></v-text-field>
        <v-text-field
          v-model="record.fechaNacimiento"
          label="Fecha de Nacimiento"
          :rules="[rules.required]"
          placeholder="YYYY-MM-DD"
        ></v-text-field>
        <v-number-input
          v-model="record.edad"
          label="Edad"
          :rules="[rules.required]"
          :min="1"
          :max="25"
        ></v-number-input>
        <v-radio-group
          v-model="record.sexo"
          label="Sexo"
          :rules="[rules.required]"
          inline
        >
          <v-radio label="Femenino" value="F"></v-radio>
          <v-radio label="Masculino" value="M"></v-radio>
        </v-radio-group>
        <v-number-input
          v-model="record.docIdentidad"
          label="Documento de Identidad"
          :rules="[rules.required]"
          control-variant="hidden"
        ></v-number-input>
        <v-text-field
          v-model="record.ciudadNacimiento"
          label="Ciudad de Nacimiento"
          :rules="[rules.required]"
        ></v-text-field>
        <v-text-field
          v-model="record.telefono"
          label="Teléfono"
          :rules="[rules.required]"
        ></v-text-field>
        <v-text-field
          v-model="record.correo"
          label="Correo Electrónico"
          :rules="[rules.required, rules.email]"
        ></v-text-field>
        <v-switch color="light-green" v-model="record.activo" label="Activo"></v-switch>
        
        <!-- <v-select
          v-model="estudianteDetalleRecord.idCurso"
          label="Curso"
          :items="cursos"
          item-title="nombre"
          item-value="idCurso"
        ></v-select>
        
        <v-select
          v-model="estudianteDetalleRecord.idAcudiente"
          label="Acudiente"
          :items="items"
          item-title="docIdentidad"
          item-value="idUsuario"
        ></v-select> -->
        
        <v-btn class="mt-2" @click="dialog = false" block>Cancelar</v-btn>
        <v-btn class="mt-2" type="submit" block color="green">Aceptar</v-btn>
      </v-form>
      
    </v-card>
  </v-dialog>
  
  <v-dialog v-model="confirmDialog.enabled" max-width="500">
    <v-card :title="`¿Eliminar ${selected.length === 0 ? 'el Usuario' : 'todos los Usuarios'}?`" 
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

const API_URL_ROLES = "http://localhost:8080/api/roles";
const roles = ref([]);

const API_URL_CURSOS = "http://localhost:8080/api/cursos";
const cursos = ref([]);

const API_URL_USUARIOS = "http://localhost:8080/api/usuarios/usuarios";
const API_URL_ESTUDIANTES = "http://localhost:8080/api/usuarios/estudiantes-detalles";
const items = ref([]);
const loading = ref(true);
const selected = ref([]);
const headers = [
  { title: "ID", key: "idUsuario" },
  { title: "Rol", key: "idRol" },
  { title: "Usuario", key: "usuario" },
  { title: "Clave", key: "clave" },
  { title: "Nombres", key: "nombres" },
  { title: "Apellidos", key: "apellidos" },
  { title: "Fecha de Nacimiento", key: "fechaNacimiento" },
  { title: "Edad", key: "edad" },
  { title: "Sexo", key: "sexo" },
  { title: "Doc. Identidad", key: "docIdentidad" },
  { title: "Ciudad de Nacimiento", key: "ciudadNacimiento" },
  { title: "Teléfono", key: "telefono" },
  { title: "Correo", key: "correo" },
  { title: "Fecha de Registro", key: "fechaRegistro" },
  { title: "Activo", key: "activo" },
  { title: "Acciones", key: "acciones" },
];
const FORM_DATA = {
  idUsuario: null,
  idRol: null,
  usuario: "",
  clave: "",
  nombres: "",
  apellidos: "",
  fechaNacimiento: "",
  edad: null,
  sexo: "",
  docIdentidad: null,
  ciudadNacimiento: "",
  telefono: "",
  correo: "",
  fechaRegistro: null,
  activo: true,
};

const DETALLE_FORM_DATA = {
  idDetalle: null,
  idUsuario: null,
  idCurso: null,
  idAcudiente: null
};
const passVisible = ref(false)
const estudianteDetalleRecord = ref(DETALLE_FORM_DATA);

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
  required: value => !!value || 'Campo requerido',
  email: value => {
    const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    return pattern.test(value) || 'Correo electrónico invalido'
  }
};

function add() {
  editing.value = false;
  record.value = { ...FORM_DATA };
  dialog.value = true;
  
  estudianteDetalleRecord.value = {...DETALLE_FORM_DATA};
}

function edit(item) {
  editing.value = true;
  record.value = { ...item };
  dialog.value = true;
}

async function remove(id) {
  await axios
    .delete(`${API_URL_USUARIOS}/delete/${id}`)
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
      selected.value.map((usuario) =>
        axios.delete(`${API_URL_USUARIOS}/delete/${usuario.idUsuario}`)
      )
    );
    showSnackbar("Usuarios eliminados correctamente");
    selected.value = [];
  } catch (error) {
    showSnackbar("Error al eliminar los usuarios", "error");
  }
  fetch();
  confirmDialog.value.enabled = false;
  confirmDialog.value.id = null;
}

async function save() {
  if (editing.value) {
    await axios
      .put(`${API_URL_USUARIOS}/update/${record.value.idUsuario}`, record.value)
      .then((res) => {
        showSnackbar(res.data);
        dialog.value = false;
      })
      .catch((error) => {
        showSnackbar(error.response.data, "error");
      });
  } else {
    await axios
      .post(`${API_URL_USUARIOS}/create`, record.value)
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
  if (e.target[1].value === "") valid.value = false;
  if (e.target[2].value === "") valid.value = false;
  if (e.target[3].value === "") valid.value = false;
  if (e.target[4].value === "") valid.value = false;
  if (e.target[5].value === "") valid.value = false;
  if (e.target[6].value === "") valid.value = false;
  if (e.target[9].value === "" && e.target[10].value === "") valid.value = false;
  if (e.target[11].value === "") valid.value = false;
  if (e.target[12].value === "") valid.value = false;
  if (e.target[13].value === "") valid.value = false;
  if (e.target[14].value === "") valid.value = false;
  
  if (valid.value) {
    save();
  } else {
    showSnackbar("Campos incompletos", "error");
  }
  
  valid.value = true;
}

const fetch = async () => {
  await axios.get(`${API_URL_USUARIOS}/getall`).then((res) => {
    items.value = res.data;
    loading.value = false;
  });
};

const fetchRoles = async () => {
  await axios.get(`${API_URL_ROLES}/getall`).then((res) => {
    roles.value = res.data;
    loading.value = false;
  });
}

const fetchCursos = async () => {
  await axios.get(`${API_URL_CURSOS}/getall`).then((res) => {
    cursos.value = res.data;
    loading.value = false;
  });
}

onMounted(() => {
  fetch();
  fetchRoles();
  fetchCursos();
});
</script>
