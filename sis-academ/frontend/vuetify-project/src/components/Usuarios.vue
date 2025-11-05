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
    :items-per-page="-1"
    :show-select="!soloLectura"
    multi-sort
  >
    <template v-slot:top>
      <v-toolbar flat class="rounded">
        <v-toolbar-title>
          <v-icon start>mdi-account-school</v-icon>
          Gestión de Usuarios
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
          Agregar un Usuario
        </v-btn>
        <v-btn
          v-if="!soloLectura"
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
    
    <template v-slot:item.clave="{ item }">
      -
    </template>
    
    <template v-slot:item.activo="{ item }">
      <v-chip v-if="item.activo" color="green">Sí</v-chip>
      <v-chip v-if="!item.activo" color="red">No</v-chip>
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
          :max="80"
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
        
        <v-select
          v-if="record.idRol == 5"
          v-model="record.idCurso"
          label="Curso"
          :items="cursos"
          item-title="nombre"
          item-value="idCurso"
        ></v-select>
        
        <v-select
          v-if="record.idRol == 5"
          v-model="record.idAcudiente"
          label="Acudiente"
          :items="acudientes"
          item-title="nombreAcudiente"
          item-value="idUsuario"
        ></v-select>
        
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
import { ref, onMounted, shallowRef, computed } from "vue";
import axios from "axios";
import bcrypt from "bcryptjs";

const userData = ref(null);
const soloLectura = computed(() => {
  if (!userData.value) return false;
  return userData.value.permisos?.soloLectura || false;
});

const acudientes = ref([]);

const API_URL_ROLES = "http://localhost:8080/api/roles";
const roles = ref([]);

const API_URL_CURSOS = "http://localhost:8080/api/cursos";
const cursos = ref([]);

const API_URL_ESTUDIANTES = "http://localhost:8080/api/usuarios/estudiantes-detalles";
const estudiantes = ref([]);

const API_URL_USUARIOS = "http://localhost:8080/api/usuarios/usuarios";
const items = ref([]);
const loading = ref(true);
const selected = ref([]);
const headers = [
  { title: "ID", key: "idUsuario" },
  // { title: "ID Rol", key: "idRol" },
  { title: "Rol", key: "nombreRol" },
  { title: "Usuario", key: "usuario" },
  // { title: "Clave", key: "clave" },
  { title: "Nombres", key: "nombres" },
  { title: "Apellidos", key: "apellidos" },
  { title: "Fecha de Nacimiento", key: "fechaNacimiento" },
  { title: "Edad", key: "edad" },
  { title: "Sexo", key: "sexo" },
  { title: "Doc. Identidad", key: "docIdentidad" },
  // { title: "Ciudad de Nacimiento", key: "ciudadNacimiento" },
  { title: "Teléfono", key: "telefono" },
  { title: "Correo", key: "correo" },
  // { title: "Fecha de Registro", key: "fechaRegistro" },
  { title: "Activo", key: "activo" },
  // { title: "ID Curso", key: "idCurso" },
  { title: "Curso", key: "nombreCurso" },
  // { title: "ID Acudiente", key: "idAcudiente" },
  { title: "Acudiente", key: "docAcudiente" },
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
  
  idDetalle: null,
  idCurso: null,
  idAcudiente: null
};
const search = ref('');

const passVisible = ref(false)

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
}

function edit(item) {
  editing.value = true;
  record.value = { ...item };
  dialog.value = true;
}

async function remove(id) {
  
  const usuario = items.value.find(u => u.idUsuario === id);
  
  if (usuario && usuario.idDetalle) {
    await axios.delete(`${API_URL_ESTUDIANTES}/delete/${usuario.idDetalle}`);
  }
  
  await axios
    .delete(`${API_URL_USUARIOS}/delete/${id}`)
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
      selected.value.map((usuario) => {
        
        if (usuario.idDetalle) {
          axios.delete(`${API_URL_ESTUDIANTES}/delete/${usuario.idDetalle}`);
        }
        
        axios.delete(`${API_URL_USUARIOS}/delete/${usuario.idUsuario}`);  
      })
    );
    showSnackbar("Usuarios eliminados correctamente");
    selected.value = [];
  } catch (error) {
    showSnackbar("Error al eliminar los usuarios", "error");
  }
  fetchAll();
  confirmDialog.value.enabled = false;
  confirmDialog.value.id = null;
}

async function save() {
  const usuarioData = {
    idUsuario: record.value.idUsuario,
    idRol: record.value.idRol,
    usuario: record.value.usuario,
    clave: record.value.clave,
    nombres: record.value.nombres,
    apellidos: record.value.apellidos,
    fechaNacimiento: record.value.fechaNacimiento,
    edad: record.value.edad,
    sexo: record.value.sexo,
    docIdentidad: record.value.docIdentidad,
    ciudadNacimiento: record.value.ciudadNacimiento,
    telefono: record.value.telefono,
    correo: record.value.correo,
    fechaRegistro: record.value.fechaRegistro,
    activo: record.value.activo
  };
  
  // Hash de la contraseña si se proporciona
  if (usuarioData.clave && usuarioData.clave.trim() !== "") {
    try {
      const salt = await bcrypt.genSalt(10);
      usuarioData.clave = await bcrypt.hash(usuarioData.clave, salt);
      console.log(usuarioData.clave);
    } catch (error) {
      showSnackbar("Error al hashear la contraseña", "error");
      return;
    }
  }
  
  const detalleData = {
    idDetalle: record.value.idDetalle,
    idUsuario: record.value.idUsuario,
    idCurso: record.value.idCurso,
    idAcudiente: record.value.idAcudiente
  };
  
  const esEstudiante = record.value.idRol === 5;
  
  if (editing.value) {
    // Actualizar Usuario
    await axios
      .put(`${API_URL_USUARIOS}/update/${record.value.idUsuario}`, usuarioData)
      .then((res) => {
        showSnackbar(res.data);
        dialog.value = false;
      })
      .catch((error) => {
        showSnackbar(error.response.data, "error");
      });
    
    // Si es estudiante, actuaizar tambien Estudiante_Detalle
    if (esEstudiante) {
      if (record.value.idDetalle) {
        // Ya existe un detalle, actualizarlo
        await axios
          .put(`${API_URL_ESTUDIANTES}/update/${record.value.idDetalle}`, detalleData)
          .then((res) => {
            showSnackbar(res.data);
          })
          .catch((error) => {
            showSnackbar(error.response.data, "error");
          });
      } else {
        // No existe detalle, crearlo
        await axios
          .post(`${API_URL_ESTUDIANTES}/create`, detalleData)
          .then((res) => {
            showSnackbar(res.data);
          })
          .catch((error) => {
            showSnackbar(error.response.data, "error");
          });
      }
    } else if (record.value.idDetalle) {
      // Si ya no es un estudiante, pero tenía un detalle, eliminarlo
      await axios
        .delete(`${API_URL_ESTUDIANTES}/delete/${record.value.idDetalle}`)
        .then((res) => {
          showSnackbar(res.data);
        })
        .catch((error) => {
          showSnackbar(error.response.data, "error");
        });
    } 
  } else {
    await axios
      .post(`${API_URL_USUARIOS}/create`, usuarioData)
      .then((res) => {
        showSnackbar(res.data);
        dialog.value = false;
      })
      .catch((error) => {
        showSnackbar(error.response.data, "error");
      });
    
    // Si es estudiante, crear también el registro en Estudiante_Detalle
    if (esEstudiante) {
      const res = await axios.get(`${API_URL_USUARIOS}/getall`);
      const nuevoUsuario = res.data.find(u => u.docIdentidad === usuarioData.docIdentidad);
      detalleData.idUsuario = nuevoUsuario.idUsuario;
      
      await axios
        .post(`${API_URL_ESTUDIANTES}/create`, detalleData)
        .then((res) => {
          showSnackbar(res.data);
        })
        .catch((error) => {
          showSnackbar(error.response.data, "error");
        });
    }
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

const fetchAll = async () => {
  Promise.all([
    axios.get(`${API_URL_ROLES}/getall`),
    axios.get(`${API_URL_CURSOS}/getall`),
    axios.get(`${API_URL_ESTUDIANTES}/getall`),
    axios.get(`${API_URL_USUARIOS}/getall`)
  ]).then(([rolesRes, cursosRes, estudiantesRes, usuariosRes]) => {
    roles.value = rolesRes.data;
    cursos.value = cursosRes.data;
    estudiantes.value = estudiantesRes.data;
    const usuariosCopy = usuariosRes.data;
    
    items.value = usuariosRes.data.map(item => {
      const rol = roles.value.find(r => r.idRol === item.idRol);
      const estudiante = estudiantes.value.find(e => e.idUsuario === item.idUsuario);
      const curso = estudiante ? cursos.value.find(c => c.idCurso === estudiante.idCurso) : null;
      const acudiente = estudiante ? usuariosCopy.find(a => a.idUsuario === estudiante.idAcudiente) : null;
      
      return {
        ...item,
        nombreRol: rol ? rol.nombre : '...',
        idDetalle: estudiante?.idDetalle ?? null,
        idCurso: curso?.idCurso ?? '-',
        nombreCurso: curso?.nombre ?? '-',
        idAcudiente: acudiente?.idUsuario ?? '-',
        docAcudiente: acudiente? acudiente.docIdentidad + " - " + acudiente.nombres + " " + acudiente.apellidos : '-'
      }
    });
    
    acudientes.value = usuariosRes.data.filter(item => item.idRol === 4);
    acudientes.value = acudientes.value.map(item => {
      return {
        ...item,
        nombreAcudiente: item.docIdentidad + " - " + item.nombres + " " + item.apellidos
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
