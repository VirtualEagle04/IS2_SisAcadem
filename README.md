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
        <th>
        Item
        </th>
        <th>
        Nombre Contenedor
        </th>
        <th>
        Puerto
        </th>
        <th>
        Base de Datos
        </th>
        <th>
        API Endpoint
        </th>
    </tr>
    <tr>
        <td>
        Base de Datos MySQL
        </td>
        <td>
        mysql-db
        </td>
        <td>
        3306
        </td>
        <td>
        -
        </td>
        <td>
        -
        </td>
    </tr>
    <tr>
        <td>
        Estudiante Service
        </td>
        <td>
        estudiante-service
        </td>
        <td>
        8081
        </td>
        <td>
        estudiante
        </td>
        <td>
        /api/estudiantes
        </td>
    </tr>
    <tr>
        <td>
        Grado Service
        </td>
        <td>
        grado-service
        </td>
        <td>
        8082
        </td>
        <td>
        grado
        </td>
        <td>
        /api/grados
        </td>
    </tr>
    <tr>
        <td>
        Curso Service
        </td>
        <td>
        curso-service
        </td>
        <td>
        8083
        </td>
        <td>
        curso
        </td>
        <td>
        /api/cursos
        </td>
    </tr>
    <tr>
        <td>
        Administrativo Service
        </td>
        <td>
        administrativo-service
        </td>
        <td>
        8084
        </td>
        <td>
        administrativo
        </td>
        <td>
        /api/administrativos
        </td>
    </tr>
    <tr>
        <td>
        Acudiente Service
        </td>
        <td>
        acudiente-service
        </td>
        <td>
        8085
        </td>
        <td>
        acudiente
        </td>
        <td>
        /api/acudientes
        </td>
    </tr>
</table>
