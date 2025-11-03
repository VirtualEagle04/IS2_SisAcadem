<template>
  <v-container class="fill-height" fluid width="1500">
    <v-row align="center" justify="center">
      <v-col cols="12" sm="8" md="4">
        <v-card class="elevation-12">
          <v-toolbar color="grey-darken-1">
            <v-toolbar-title align="center">
              <v-icon start>mdi-school</v-icon>
              Sistema de Gestión Académica
            </v-toolbar-title>
          </v-toolbar>
          <v-img src="../assets/logo.jpg" class="mt-5 mb-5 ml-5 mr-5"></v-img>
          <v-card-text>
            <v-form @submit.prevent="handleLogin">
              <v-text-field
                v-model="credentials.usuario"
                label="Usuario"
                prepend-icon="mdi-account"
                :rules="[rules.required]"
                required
              ></v-text-field>

              <v-text-field
                v-model="credentials.clave"
                label="Contraseña"
                prepend-icon="mdi-lock"
                :append-inner-icon="passVisible ? 'mdi-eye-off' : 'mdi-eye'"
                :type="passVisible ? 'text' : 'password'"
                :rules="[rules.required]"
                @click:append-inner="passVisible = !passVisible"
                required
              ></v-text-field>

              <v-btn
                class="mt-4"
                color="blue"
                type="submit"
                block
                :loading="loading"
              >
                Iniciar Sesión
              </v-btn>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <v-snackbar v-model="snackbar" :color="snackbarColor" timeout="3000">
      {{ snackbarMsg }}
    </v-snackbar>
  </v-container>
  
  <v-footer class="d-flex align-center justify-center ga-2 flex-wrap flex-grow-1 py-3">
    <div class="flex-1-0-100 text-center mt-2">
        {{ new Date().getFullYear() }} - <strong>Hozho Inc.</strong>
    </div>
  </v-footer>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';
import bcrypt from 'bcryptjs';

const emit = defineEmits(['login-success']);

const API_URL_USUARIOS = "http://localhost:8080/api/usuarios/usuarios";
const API_URL_ROLES = "http://localhost:8080/api/roles";

const credentials = ref({
  usuario: '',
  clave: ''
});

const passVisible = ref(false);
const loading = ref(false);

const snackbar = ref(false);
const snackbarColor = ref("success");
const snackbarMsg = ref("");

const rules = {
  required: value => !!value || 'Campo requerido'
};

function showSnackbar(msg, color = "success") {
  snackbarMsg.value = msg;
  snackbarColor.value = color;
  snackbar.value = true;
}

async function handleLogin() {
  if (!credentials.value.usuario || !credentials.value.clave) {
    showSnackbar("Por favor complete todos los campos", "error");
    return;
  }

  loading.value = true;

  try {
    // Obtener todos los usuarios
    const usuariosRes = await axios.get(`${API_URL_USUARIOS}/getall`);
    const usuarios = usuariosRes.data;

    // Buscar el usuario por nombre de usuario
    const usuario = usuarios.find(u => u.usuario === credentials.value.usuario);

    if (!usuario) {
      showSnackbar("Usuario o contraseña incorrectos", "error");
      loading.value = false;
      return;
    }

    // Verificar si el usuario está activo
    if (!usuario.activo) {
      showSnackbar("Usuario inactivo. Contacte al administrador", "error");
      loading.value = false;
      return;
    }

    // Comparar la contraseña con bcrypt
    const passwordMatch = await bcrypt.compare(credentials.value.clave, usuario.clave);

    if (!passwordMatch) {
      showSnackbar("Usuario o contraseña incorrectos", "error");
      loading.value = false;
      return;
    }

    // Obtener el rol del usuario
    const rolRes = await axios.get(`${API_URL_ROLES}/getall`);
    const rol = rolRes.data.find(r => r.idRol === usuario.idRol);

    if (!rol) {
      showSnackbar("Error al obtener permisos del usuario", "error");
      loading.value = false;
      return;
    }

    // Guardar en localStorage
    const userData = {
      idUsuario: usuario.idUsuario,
      usuario: usuario.usuario,
      nombres: usuario.nombres,
      apellidos: usuario.apellidos,
      idRol: usuario.idRol,
      permisos: {
        soloLectura: rol.soloLectura,
        rol: rol.rol,
        usuario: rol.usuario,
        grado: rol.grado,
        curso: rol.curso,
        materia: rol.materia,
        actividad: rol.actividad,
        horario: rol.horario,
        nota: rol.nota,
        asistencia: rol.asistencia,
        matricula: rol.matricula
      }
    };

    localStorage.setItem('userData', JSON.stringify(userData));

    showSnackbar("Inicio de sesión exitoso", "success");
    
    // Emitir evento de login exitoso
    setTimeout(() => {
      emit('login-success', userData);
    }, 1000);

  } catch (error) {
    console.error("Error en login:", error);
    showSnackbar("Error al iniciar sesión", "error");
  } finally {
    loading.value = false;
  }
}
</script>