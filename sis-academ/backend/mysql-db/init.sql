CREATE DATABASE IF NOT EXISTS rol;
CREATE DATABASE IF NOT EXISTS usuario;

CREATE DATABASE IF NOT EXISTS grado;
CREATE DATABASE IF NOT EXISTS curso;
CREATE DATABASE IF NOT EXISTS materia;

CREATE DATABASE IF NOT EXISTS actividad;

CREATE DATABASE IF NOT EXISTS horario;
CREATE DATABASE IF NOT EXISTS nota;
CREATE DATABASE IF NOT EXISTS asistencia;
CREATE DATABASE IF NOT EXISTS matricula;



USE rol;

CREATE TABLE IF NOT EXISTS rol(
	id_rol BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    solo_lectura BOOLEAN DEFAULT TRUE,
	rol BOOLEAN DEFAULT FALSE,
	usuario BOOLEAN DEFAULT FALSE,
	grado BOOLEAN DEFAULT FALSE,
	curso BOOLEAN DEFAULT FALSE,
	materia BOOLEAN DEFAULT FALSE,
	actividad BOOLEAN DEFAULT FALSE,
	estudiante BOOLEAN DEFAULT FALSE,
	horario BOOLEAN DEFAULT FALSE,
	nota BOOLEAN DEFAULT FALSE,
	asistencia BOOLEAN DEFAULT FALSE,
	matricula BOOLEAN DEFAULT FALSE
);

INSERT INTO rol (nombre, solo_lectura, rol, usuario, grado, curso, materia, actividad, estudiante, horario, nota, asistencia, matricula)
VALUES
('Administrador', FALSE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE),
('Coordinador', FALSE, FALSE, FALSE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE),
('Docente', FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, TRUE, TRUE, FALSE, TRUE, TRUE, FALSE),
('Acudiente', TRUE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE),
('Estudiante', TRUE, FALSE, FALSE, FALSE, FALSE, FALSE, TRUE, FALSE, TRUE, TRUE, FALSE, FALSE);


USE usuario;

CREATE TABLE IF NOT EXISTS usuario(
	id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
	id_rol BIGINT NOT NULL,
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
	correo VARCHAR(255) UNIQUE,
	fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	activo BOOLEAN DEFAULT TRUE,
	FOREIGN KEY (id_rol) REFERENCES rol.rol(id_rol)
);

INSERT INTO usuario (id_rol, usuario, clave, nombres, apellidos, fecha_nacimiento, edad, sexo, doc_identidad, ciudad_nacimiento, telefono, correo)
VALUES
(1, 'admin', 'admin', 'ADMIN', 'ADMIN', '2000-01-01', 25, 'M', 1234567890, 'Bogota', '3003344556', 'admin@ejemplo.com'),

(3, 'adoe', 'teachpass123', 'Alice', 'Doe', '1980-03-10', 43, 'F', 1122334451, 'Cali', '3001122334', 'adoe@ejemplo.com'),
(3, 'bsmith', 'teachpass456', 'Bob', 'Smith', '1975-07-25', 48, 'M', 5566778892, 'Barranquilla', '3005566778', 'bsmith@ejemplo.com'),

(4, 'cdoe', 'parentpass123', 'Charlie', 'Doe', '1970-11-30', 53, 'M', 2233445563, 'Pereira', '3002233445', 'cdoe@ejemplo.com'),
(4, 'dsmith', 'parentpass456', 'Diana', 'Smith', '1972-02-14', 51, 'F', 6677889901, 'Manizales', '3006677889', 'dsmith@ejemplo.com'),

(5, 'jdoe', 'password123', 'John', 'Doe', '2005-05-15', 18, 'M', 1234567891, 'Bogota', '3001234567', 'jdoe@ejemplo.com'),
(5, 'msmith', 'password456', 'Maria', 'Smith', '2006-08-20', 17, 'F', 9876543211, 'Medellín', '3009876543', 'msmith@ejemplo.com');



USE grado;

CREATE TABLE IF NOT EXISTS grado(
    id_grado BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

INSERT INTO grado (nombre)
VALUES
('Primero de Primaria'),
('Segundo de Primaria'),
('Tercero de Primaria'),
('Cuarto de Primaria'),
('Quinto de Primaria'),
('Sexto de Bachillerato'),
('Septimo de Bachillerato'),
('Octavo de Bachillerato'),
('Noveno de Bachillerato'),
('Décimo de Bachillerato'),
('Once de Bachillerato');



USE curso;

CREATE TABLE IF NOT EXISTS curso(
    id_curso BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_grado BIGINT NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    FOREIGN KEY (id_grado) REFERENCES grado.grado(id_grado)
);

INSERT INTO curso (id_grado, nombre)
VALUES
(1, 'A'),
(1, 'B'),
(1, 'C'),

(2, 'A'),
(2, 'B'),
(2, 'C'),

(3, 'A'),
(3, 'B'),
(3, 'C'),

(4, 'A'),
(4, 'B'),
(4, 'C'),

(5, 'A'),
(5, 'B'),
(5, 'C'),

(6, 'A'),
(6, 'B'),

(7, 'A'),
(7, 'B'),

(8, 'A'),
(8, 'B'),

(9, 'A'),
(9, 'B'),

(10, 'A'),
(10, 'B'),

(11, 'A'),
(11, 'B');



USE usuario;
CREATE TABLE IF NOT EXISTS estudiante_detalle(
	id_detalle BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario BIGINT NOT NULL,
    id_curso BIGINT NOT NULL,
    id_acudiente BIGINT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario.usuario(id_usuario),
    FOREIGN KEY (id_curso) REFERENCES curso.curso(id_curso),
    FOREIGN KEY (id_acudiente) REFERENCES usuario.usuario(id_usuario)
);



USE materia;

CREATE TABLE IF NOT EXISTS materia (
    id_materia BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_docente BIGINT NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    FOREIGN KEY (id_docente) REFERENCES usuario.usuario(id_usuario)
);

INSERT INTO materia (id_docente, nombre, descripcion)
VALUES
(2, 'Matemáticas', 'Curso de matemáticas básicas'),
(3, 'Historia', 'Curso de historia mundial');



USE grado;
CREATE TABLE IF NOT EXISTS materias_grado (
	id_materia_grado BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_grado BIGINT NOT NULL,
    id_materia BIGINT NOT NULL,
    FOREIGN KEY (id_grado) REFERENCES grado.grado(id_grado),
    FOREIGN KEY (id_materia) REFERENCES materia.materia(id_materia)
);



USE actividad;

CREATE TABLE IF NOT EXISTS actividad (
    id_actividad BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_materia BIGINT NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    porcentaje INT,
    FOREIGN KEY (id_materia) REFERENCES materia.materia (id_materia)
);

INSERT INTO actividad (id_materia, nombre, descripcion, porcentaje)
VALUES
(1, 'Examen Parcial', 'Examen parcial de matemáticas', 30),
(2, 'Trabajo Final', 'Trabajo final de historia', 40);



USE horario;

CREATE TABLE IF NOT EXISTS periodo_academico(
    id_periodo BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL
);


CREATE TABLE IF NOT EXISTS horario_clase(
    id_horario BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_grado BIGINT NOT NULL,
    id_materia BIGINT NOT NULL,
    dia_semana ENUM('Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes') NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    salon VARCHAR(50),
    FOREIGN KEY (id_grado) REFERENCES grado.grado(id_grado),
    FOREIGN KEY (id_materia) REFERENCES materia.materia(id_materia)
);



USE nota;

CREATE TABLE IF NOT EXISTS nota(
    id_nota BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_actividad BIGINT NOT NULL,
    id_estudiante BIGINT NOT NULL,
    id_periodo BIGINT NOT NULL,
    calificacion FLOAT NOT NULL,
    observacion TEXT,
    tipo ENUM('Normal', 'Recuperación') NOT NULL,
    FOREIGN KEY (id_actividad) REFERENCES actividad.actividad(id_actividad),
    FOREIGN KEY (id_estudiante) REFERENCES usuario.usuario(id_usuario),
    FOREIGN KEY (id_periodo) REFERENCES horario.periodo_academico(id_periodo)
);



USE asistencia;

CREATE TABLE IF NOT EXISTS asistencia(
    id_asistencia BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_estudiante BIGINT NOT NULL,
    id_clase BIGINT NOT NULL,
    estado ENUM('Presente', 'Ausente', 'Tarde', 'Excusado') NOT NULL,
    observacion TEXT,
    FOREIGN KEY (id_estudiante) REFERENCES usuario.usuario(id_usuario),
    FOREIGN KEY (id_clase) REFERENCES horario.horario_clase(id_horario)
);



USE matricula;

CREATE TABLE IF NOT EXISTS matricula(
    id_matricula BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_estudiante BIGINT NOT NULL,
    id_periodo BIGINT NOT NULL,
    fecha_matricula DATE NOT NULL,
    estado ENUM('Activa', 'Inactiva', 'Retirado') NOT NULL,
    FOREIGN KEY (id_estudiante) REFERENCES usuario.usuario(id_usuario),
    FOREIGN KEY (id_periodo) REFERENCES horario.periodo_academico(id_periodo)
);