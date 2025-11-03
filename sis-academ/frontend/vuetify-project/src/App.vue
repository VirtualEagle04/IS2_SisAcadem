<template>
  <v-app>
    <!-- Mostrar Login si no está autenticado -->
    <Login v-if="!isAuthenticated" @login-success="handleLoginSuccess" />
    
    <!-- Mostrar la aplicación si está autenticado -->
    <template v-else>
      <v-navigation-drawer app expand-on-hover rail width="350">
        <v-list>
          <!-- Header con información del usuario -->
          <v-list-item class="mb-2">
            <template v-slot:prepend>
              <v-avatar color="primary">
                <span class="text-h6">{{ userInitials }}</span>
              </v-avatar>
            </template>
            <v-list-item-title>{{ userData.nombres }} {{ userData.apellidos }}</v-list-item-title>
            <v-list-item-subtitle>{{ userData.usuario }}</v-list-item-subtitle>
          </v-list-item>
          
          <v-divider class="mb-2"></v-divider>
          
          <!-- Items del menú basados en permisos -->
          <v-list-item 
            v-if="permisos.rol" 
            prepend-icon="mdi-account-cog" 
            title="Roles" 
            value="rol" 
            @click="selected = 'rol'"
          ></v-list-item>
          
          <v-list-item 
            v-if="permisos.usuario" 
            prepend-icon="mdi-account-school" 
            title="Usuarios" 
            value="usuario" 
            @click="selected = 'usuario'"
          ></v-list-item>
          
          <v-list-item 
            v-if="permisos.grado" 
            prepend-icon="mdi-chevron-triple-up" 
            title="Grados" 
            value="grado" 
            @click="selected = 'grado'"
          ></v-list-item>
          
          <v-list-item 
            v-if="permisos.curso" 
            prepend-icon="mdi-account-group" 
            title="Cursos" 
            value="curso" 
            @click="selected = 'curso'"
          ></v-list-item>
          
          <v-list-item 
            v-if="permisos.materia" 
            prepend-icon="mdi-book-multiple" 
            title="Materias" 
            value="materia" 
            @click="selected = 'materia'"
          ></v-list-item>
          
          <v-list-item 
            v-if="permisos.actividad" 
            prepend-icon="mdi-format-list-checkbox" 
            title="Actividades" 
            value="actividad" 
            @click="selected = 'actividad'"
          ></v-list-item>
          
          <v-list-group v-if="permisos.horario" value="Horarios">
            <template v-slot:activator="{ props }">
              <v-list-item v-bind="props" prepend-icon="mdi-calendar-month" title="Horarios"></v-list-item>
            </template>
            <v-list-item 
              prepend-icon="mdi-calendar-range" 
              title="Periodos Académicos" 
              value="periodo-academico" 
              @click="selected = 'periodo-academico'"
            ></v-list-item>
            <v-list-item 
              prepend-icon="mdi-google-classroom" 
              title="Horarios de Clase" 
              value="horario-clase" 
              @click="selected = 'horario-clase'"
            ></v-list-item>
          </v-list-group>
          
          <v-list-item 
            v-if="permisos.nota" 
            prepend-icon="mdi-numeric-5-box-multiple" 
            title="Notas" 
            value="nota" 
            @click="selected = 'nota'"
          ></v-list-item>
          
          <v-list-item 
            v-if="permisos.asistencia" 
            prepend-icon="mdi-calendar-clock" 
            title="Asistencias" 
            value="asistencia" 
            @click="selected = 'asistencia'"
          ></v-list-item>
          
          <v-list-item 
            v-if="permisos.matricula" 
            prepend-icon="mdi-file-sign" 
            title="Matriculas" 
            value="matricula" 
            @click="selected = 'matricula'"
          ></v-list-item>
          
          <v-divider class="my-2"></v-divider>
          
          <!-- Botón de cerrar sesión -->
          <v-list-item 
            prepend-icon="mdi-logout" 
            title="Cerrar Sesión" 
            @click="logout"
            color="error"
          ></v-list-item>
        </v-list>
      </v-navigation-drawer>
      
      <v-main class="ml-4 mr-4 mt-4 mb-4 rounded-lg">
        <Roles v-if="selected == 'rol' && permisos.rol"></Roles>
        <Usuarios v-if="selected == 'usuario' && permisos.usuario"></Usuarios>
        <Grados v-if="selected == 'grado' && permisos.grado"></Grados>
        <Cursos v-if="selected == 'curso' && permisos.curso"></Cursos>
        <Materias v-if="selected == 'materia' && permisos.materia"></Materias>
        <Actividades v-if="selected == 'actividad' && permisos.actividad"></Actividades>
        <PeriodosAcademicos v-if="selected == 'periodo-academico' && permisos.horario"></PeriodosAcademicos>
        <HorariosClase v-if="selected == 'horario-clase' && permisos.horario"></HorariosClase>
        <Notas v-if="selected == 'nota' && permisos.nota"></Notas>
        <Asistencias v-if="selected == 'asistencia' && permisos.asistencia"></Asistencias>
        <Matriculas v-if="selected == 'matricula' && permisos.matricula"></Matriculas>
        
        <!-- Mensaje de bienvenida si no hay selección -->
        <v-container v-if="!selected" class="fill-height">
          <v-row align="center" justify="center">
            <v-col cols="12" class="text-center">
              <v-icon size="120" color="primary">mdi-school</v-icon>
              <h1 class="text-h3 mb-4">Bienvenido al Sistema de Gestión Académica</h1>
              <p class="text-h6">Selecciona una opción del menú para comenzar</p>
            </v-col>
          </v-row>
        </v-container>
      </v-main>
    </template>
  </v-app>
</template>

<script setup>
  import { ref, computed, onMounted } from 'vue'
  import Login from './components/Login.vue';
  import Roles from './components/Roles.vue';
  import Usuarios from './components/Usuarios.vue';
  import Grados from './components/Grados.vue';
  import Cursos from './components/Cursos.vue';
  import Materias from './components/Materias.vue';
  import Actividades from './components/Actividades.vue';
  import Notas from './components/Notas.vue';
  import Asistencias from './components/Asistencias.vue';
  import Matriculas from './components/Matriculas.vue';
  import PeriodosAcademicos from './components/PeriodosAcademicos.vue';
  import HorariosClase from './components/HorariosClase.vue';
  
  const isAuthenticated = ref(false);
  const userData = ref({});
  const permisos = ref({
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
    matricula: false
  });
  
  // Selección inicial basada en el primer permiso disponible
  const selected = ref(null);
  
  // Computar iniciales del usuario
  const userInitials = computed(() => {
    if (!userData.value.nombres || !userData.value.apellidos) return '?';
    return `${userData.value.nombres.charAt(0)}${userData.value.apellidos.charAt(0)}`.toUpperCase();
  });
  
  function handleLoginSuccess(user) {
    isAuthenticated.value = true;
    userData.value = user;
    permisos.value = user.permisos;
    
    // Seleccionar automáticamente la primera opción disponible
    if (permisos.value.usuario) selected.value = 'usuario';
    else if (permisos.value.rol) selected.value = 'rol';
    else if (permisos.value.grado) selected.value = 'grado';
    else if (permisos.value.curso) selected.value = 'curso';
    else if (permisos.value.materia) selected.value = 'materia';
    else if (permisos.value.actividad) selected.value = 'actividad';
    else if (permisos.value.horario) selected.value = 'periodo-academico';
    else if (permisos.value.nota) selected.value = 'nota';
    else if (permisos.value.asistencia) selected.value = 'asistencia';
    else if (permisos.value.matricula) selected.value = 'matricula';
  }
  
  function logout() {
    localStorage.removeItem('userData');
    isAuthenticated.value = false;
    userData.value = {};
    permisos.value = {
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
      matricula: false
    };
    selected.value = null;
  }
  
  // Verificar si hay una sesión guardada al cargar
  onMounted(() => {
    const savedUser = localStorage.getItem('userData');
    if (savedUser) {
      try {
        const user = JSON.parse(savedUser);
        handleLoginSuccess(user);
      } catch (error) {
        console.error('Error al cargar sesión guardada:', error);
        localStorage.removeItem('userData');
      }
    }
  });
</script>