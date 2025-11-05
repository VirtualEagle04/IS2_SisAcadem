# IS2_SisAcadem

Repositorio para el Sistema de Gestión Académica para el colegio 'Camino al Saber'.

## ¿Cómo correr el proyecto?

### Backend

1. En la terminal ubicarse en el directorio `/backend`, tipo así:
   - `cd C:\Users\<su-nombre-usuario>\OneDrive\Documents\GitHub\IS2_SisAcadem\sis-academ\backend`
   - La dirección no necesariamente tiene que ser esa.
   
2. Ejecutar el comando de Docker compose para limpiar previas builds.
    - `docker-compose down -v`
    
3. Ejecutar el comando de Docker Compose para construir los contenedores. Puede tardarse un poco.
   - `docker-compose up --build`
   - Esperar a que todos los contenedores estén corriendo.
   
4. Ahora se pueden probar los REST API endpoints de los microservicios por medio del API Gateway, así:
    - `http://localhost:8080/<API Endpoint>`
    - Por ejemplo: 
        - `http://localhost:8080/api/roles/getall`
        - `http://localhost:8080/api/horarios/periodos-academicos/getall`
        - `http://localhost:8080/api/usuarios/usuarios/getall`
        - `http://localhost:8080/api/asistencias/getall`
        - etc...

### Frontend

1. En la terminal ubicarse en el directorio `/frontend/vuetify-project`, tipo así:
   - `cd C:\Users\<su-nombre-usuario>\OneDrive\Documents\GitHub\IS2_SisAcadem\sis-academ\frontend\vuetify-project`
   - La dirección no necesariamente tiene que ser esa.
  
2. Ejecutar el comando de NPM para instalar todos los paquetes necesarios.
   - `npm install`
  
3. Ejecutar el comando de NPM para correr la aplicación Vite.
   - `npm run dev`
  
4. Ahora se puede acceder desde el navegador al aplicativo, así:
   - `http://localhost:3000`
   - Ingresar utilizando cualquiera de los siguientes credenciales para cada rol:
      -  ADMINISTRADOR (Usuario: admin, Contraseña: admin)
      -  COORDINADOR (Usuario: etorres, Contraseña: coord01)
      -  DOCENTE 1 (Usuario: adoe, Contraseña: teachpass123)
      -  DOCENTE 2 (Usuario: bsmith, Contraseña: teachpass456)
      -  ACUDIENTE 1 (Usuario: jgarcia, Contraseña: parent101)
      -  ACUDIENTE 2 (Usuario: mfernandez, Contraseña: parent102)
      -  ESTUDIANTE 1 (Usuario: sgarcia, Contraseña: student201)
      -  ESTUDIANTE 2 (Usuario: dfernandez, Contraseña: student202)

## ¿Cómo acceder a cada servicio/contenedor?

Cada contenedor tiene un puerto por donde recibe/envia información.

<table>
    <tr>
        <th>Item</th><th>Nombre Contenedor</th><th>Puerto</th><th>Base de Datos</th><th>API Endpoint</th>
    </tr>
    <tr>
        <td>Servidor Frontend</td><td>-</td><td>3000</td><td>-</td><td>-</td>
    </tr>
    <tr>
        <td>Servidor MySQL</td><td>mysql-db</td><td>3306</td><td>-</td><td>-</td>
    </tr>
    <tr>
        <td>API Gateway</td><td>api-gateway</td><td>8080</td><td>-</td><td>-</td>
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
