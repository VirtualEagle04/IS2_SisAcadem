# IS2_SisAcadem

Repositorio para el Sistema de Gestión Académica para el colegio 'Camino al Saber'.

## ¿Cómo correr el proyecto?

1. En la terminal ubicarse en el directorio `/backend`, tipo así:
   - `cd C:\Users\<su-nombre-usuario>\OneDrive\Documents\GitHub\IS2_SisAcadem\sis-academ\backend`
   - La dirección no necesariamente tiene que ser esa.
2. Ejecutar el comando de Docker Compose para construir los contenedores. Puede tardarse un poco.
   - `docker-compose up --build`

## ¿Cómo acceder a cada servicio/contenedor?

Cada contenedor tiene un puerto por donde recibe/envia información.

<table>
    <tr>
        <th>Item</th><th>Nombre Contenedor</th><th>Puerto</th><th>Base de Datos</th><th>API Endpoint</th>
    </tr>
    <tr>
        <td>Servidor MySQL</td><td>mysql-db</td><td>3306</td><td>-</td><td>-</td>
    </tr>
    <tr>
        <td>Microservicio Rol</td><td>rol-service</td><td>8081</td><td>rol</td><td>/api/roles</td>
    </tr>
    <tr>
        <td>Microservicio Usuario</td><td>usuario-service</td><td>8082</td><td>usuario</td><td>/api/usuarios</td>
    </tr>
    <tr>
        <td>Microservicio Grado</td><td>grado-service</td><td>8083</td><td>grado</td><td>/api/grados</td>
    </tr>
    <tr>
        <td>Microservicio Curso</td><td>curso-service</td><td>8084</td><td>curso</td><td>/api/cursos</td>
    </tr>
    <tr>
        <td>Microservicio Materia</td><td>materia-service</td><td>8085</td><td>materia</td><td>/api/materias</td>
    </tr>
    <tr>
        <td>Microservicio Actividad</td><td>actividad-service</td><td>8086</td><td>actividad</td><td>/api/actividades</td>
    </tr>
    <tr>
        <td>Microservicio Horario</td><td>horario-service</td><td>8087</td><td>horario</td><td>/api/horarios</td>
    </tr>
    <tr>
        <td>Microservicio Nota</td><td>nota-service</td><td>8088</td><td>nota</td><td>/api/notas</td>
    </tr>
    <tr>
        <td>Microservicio Asistencias</td><td>asistencia-service</td><td>8089</td><td>asistencia</td><td>/api/asistencias</td>
    </tr>
    <tr>
        <td>Microservicio Matricula</td><td>matricula-service</td><td>8090</td><td>matricula</td><td>/api/matriculas</td>
    </tr>
</table>
