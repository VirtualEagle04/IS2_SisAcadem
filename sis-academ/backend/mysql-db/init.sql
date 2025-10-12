CREATE DATABASE IF NOT EXISTS docente;
CREATE DATABASE IF NOT EXISTS acudiente;
CREATE DATABASE IF NOT EXISTS administrativo;
CREATE DATABASE IF NOT EXISTS materia;
CREATE DATABASE IF NOT EXISTS actividad;
CREATE DATABASE IF NOT EXISTS grado;
CREATE DATABASE IF NOT EXISTS curso;
CREATE DATABASE IF NOT EXISTS estudiante;
CREATE DATABASE IF NOT EXISTS nota;


USE docente;

CREATE TABLE IF NOT EXISTS docente(
    id_docente BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(255) NOT NULL UNIQUE,
    clave VARCHAR(255) NOT NULL,
    nombres VARCHAR(255) NOT NULL,
    apellidos VARCHAR(255) NOT NULL,
    fecha_nacimiento DATE,
    edad INT,
    sexo CHAR(1),
    doc_identidad BIGINT UNIQUE,
    ciudad_nacimiento VARCHAR(255),
    telefono VARCHAR(20),
    correo VARCHAR(255) 
);

INSERT INTO docente (usuario, clave, nombres, apellidos, fecha_nacimiento, edad, sexo, doc_identidad, ciudad_nacimiento, telefono, correo)
VALUES 
('adoe', 'teachpass123', 'Alice', 'Doe', '1980-03-10', 43, 'F', 112233445, 'Cali', '3001122334', 'adoe@example.com'),
('bsmith', 'teachpass456', 'Bob', 'Smith', '1975-07-25', 48, 'M', 556677889, 'Barranquilla', '3005566778', 'bsmith@example.com');


USE acudiente;

CREATE TABLE IF NOT EXISTS acudiente(
    id_acudiente BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(255) NOT NULL UNIQUE,
    clave VARCHAR(255) NOT NULL,
    nombres VARCHAR(255) NOT NULL,
    apellidos VARCHAR(255) NOT NULL,
    fecha_nacimiento DATE,
    edad INT,
    sexo CHAR(1),
    doc_identidad BIGINT UNIQUE,
    ciudad_nacimiento VARCHAR(255),
    telefono VARCHAR(20),
    correo VARCHAR(255)
);

INSERT INTO acudiente (usuario, clave, nombres, apellidos, fecha_nacimiento, edad, sexo, doc_identidad, ciudad_nacimiento, telefono, correo)
VALUES
('cdoe', 'parentpass123', 'Charlie', 'Doe', '1970-11-30', 53, 'M', 223344556, 'Pereira', '3002233445', 'cdoe@example.com'),
('dsmith', 'parentpass456', 'Diana', 'Smith', '1972-02-14', 51, 'F', 667788990, 'Manizales', '3006677889', 'dsmith@example.com');


USE administrativo;

CREATE TABLE IF NOT EXISTS administrativo(
    id_administrativo BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(255) NOT NULL UNIQUE,
    clave VARCHAR(255) NOT NULL,
    nombres VARCHAR(255) NOT NULL,
    apellidos VARCHAR(255) NOT NULL,
    fecha_nacimiento DATE,
    edad INT,
    sexo CHAR(1),
    doc_identidad BIGINT UNIQUE,
    ciudad_nacimiento VARCHAR(255),
    telefono VARCHAR(20),
    correo VARCHAR(255)
);

INSERT INTO administrativo (usuario, clave, nombres, apellidos, fecha_nacimiento, edad, sexo, doc_identidad, ciudad_nacimiento, telefono, correo)
VALUES
('admin1', 'adminpass123', 'Eve', 'Admin', '1985-09-05', 38, 'F', 334455667, 'Cartagena', '3003344556', 'admin1@example.com');

USE materia;

CREATE TABLE IF NOT EXISTS materia (
    id_materia BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_docente BIGINT,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    FOREIGN KEY (id_docente) REFERENCES docente.docente(id_docente)
);

INSERT INTO materia (id_docente, nombre, descripcion)
VALUES
(1, 'Matemáticas', 'Curso de matemáticas básicas'),
(2, 'Historia', 'Curso de historia mundial');


USE actividad;

CREATE TABLE IF NOT EXISTS actividad(
    id_actividad BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_materia BIGINT,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    porcentaje INT,
    FOREIGN KEY (id_materia) REFERENCES materia.materia(id_materia)
);

INSERT INTO actividad (id_materia, nombre, descripcion, porcentaje)
VALUES
(1, 'Examen Parcial', 'Examen parcial de matemáticas', 30),
(2, 'Trabajo Final', 'Trabajo final de historia', 40);


USE grado;

CREATE TABLE IF NOT EXISTS grado(
    id_grado BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

INSERT INTO grado (nombre)
VALUES
('Primero de Primaria'),
('Segundo de Primaria');


USE curso;

CREATE TABLE IF NOT EXISTS curso(
    id_curso BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_grado BIGINT,
    nombre VARCHAR(255) NOT NULL,
    FOREIGN KEY (id_grado) REFERENCES grado.grado(id_grado)
);

INSERT INTO curso (id_grado, nombre)
VALUES
(1, 'A'),
(2, 'B');


USE estudiante;

CREATE TABLE IF NOT EXISTS estudiante (
    id_estudiante BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_curso BIGINT,
    id_acudiente BIGINT,
    usuario VARCHAR(255) NOT NULL UNIQUE,
    clave VARCHAR(255) NOT NULL,
    nombres VARCHAR(255) NOT NULL,
    apellidos VARCHAR(255) NOT NULL,
    fecha_nacimiento DATE,
    edad INT,
    sexo CHAR(1),
    doc_identidad BIGINT UNIQUE,
    ciudad_nacimiento VARCHAR(255),
    telefono VARCHAR(20),
    FOREIGN KEY (id_curso) REFERENCES curso.curso(id_curso),
    FOREIGN KEY (id_acudiente) REFERENCES acudiente.acudiente(id_acudiente)
);

INSERT INTO estudiante (id_curso, id_acudiente, usuario, clave, nombres, apellidos, fecha_nacimiento, edad, sexo, doc_identidad, ciudad_nacimiento, telefono) 
VALUES 
(1, 1, 'jdoe', 'password123', 'John', 'Doe', '2005-05-15', 18, 'M', 123456789, 'Bogotá', '3001234567'),
(2, 2, 'msmith', 'password456', 'Maria', 'Smith', '2006-08-20', 17, 'F', 987654321, 'Medellín', '3009876543');


USE nota;

CREATE TABLE IF NOT EXISTS nota(
    id_nota BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_actividad BIGINT,
    id_estudiante BIGINT,
    calificacion FLOAT NOT NULL,
    observacion TEXT,
    FOREIGN KEY (id_actividad) REFERENCES actividad.actividad(id_actividad),
    FOREIGN KEY (id_estudiante) REFERENCES estudiante.estudiante(id_estudiante)
);

INSERT INTO nota (id_actividad, id_estudiante, calificacion, observacion)
VALUES
(1, 1, 4.5, 'Buen desempeño'),
(2, 2, 5.0, 'Excelente trabajo');