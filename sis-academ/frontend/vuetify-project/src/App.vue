<template>
  <v-app>
    <v-navigation-drawer app expand-on-hover rail width="350">
      <v-list>
        <v-list-item prepend-icon="mdi-account-cog" title="Roles" value="rol" @click="selected = 'rol'"></v-list-item>
        <v-list-item prepend-icon="mdi-account-school" title="Usuarios" value="usuario" @click="selected = 'usuario'"></v-list-item>
        <v-list-item prepend-icon="mdi-chevron-triple-up" title="Grados" value="grado" @click="selected = 'grado'"></v-list-item>
        <v-list-item prepend-icon="mdi-account-group" title="Cursos" value="curso" @click="selected = 'curso'"></v-list-item>
        <v-list-item prepend-icon="mdi-book-multiple" title="Materias" value="materia" @click="selected = 'materia'"></v-list-item>
        <v-list-item prepend-icon="mdi-format-list-checkbox" title="Actividades" value="actividad" @click="selected = 'actividad'"></v-list-item>
        
        <v-list-group value="Horarios">
          <template v-slot:activator="{ props }">
            <v-list-item v-bind="props" prepend-icon="mdi-calendar-month" title="Horarios"></v-list-item>
          </template>
          <v-list-item prepend-icon="mdi-calendar-range" title="Periodos Académicos" value="periodo-academico" @click="selected = 'periodo-academico'"></v-list-item>
          <v-list-item prepend-icon="mdi-google-classroom" title="Horarios de Clase" value="horario-clase" @click="selected = 'horario-clase'"></v-list-item>
        </v-list-group>
        
        <v-list-item prepend-icon="mdi-numeric-5-box-multiple" title="Notas" value="nota" @click="selected = 'nota'"></v-list-item>
        <v-list-item prepend-icon="mdi-calendar-clock" title="Asistencias" value="asistencia" @click="selected = 'asistencia'"></v-list-item>
        <v-list-item prepend-icon="mdi-file-sign" title="Matriculas" value="matricula" @click="selected = 'matricula'"></v-list-item>
      </v-list>
    </v-navigation-drawer>
    
    <v-main class="ml-4 mr-4 mt-4 mb-4 rounded-lg">
      <Roles v-if="selected == 'rol'"></Roles>
      <Usuarios v-if="selected == 'usuario'"></Usuarios>
      <Grados v-if="selected == 'grado'"></Grados>
      <Cursos v-if="selected == 'curso'"></Cursos>
      <Materias v-if="selected == 'materia'"></Materias>
      <Actividades v-if="selected == 'actividad'"></Actividades>
      <PeriodosAcademicos v-if="selected == 'periodo-academico'"></PeriodosAcademicos>
      <HorariosClase v-if="selected == 'horario-clase'"></HorariosClase>
      <Notas v-if="selected == 'nota'"></Notas>
      <Asistencias v-if="selected == 'asistencia'"></Asistencias>
      <Matriculas v-if="selected == 'matricula'"></Matriculas>
    </v-main>
  </v-app>
</template>

<script setup>
  import { ref } from 'vue'
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
  
  const selected = ref('usuario');
</script>