-- Configurar codificación UTF-8 para toda la sesión
SET NAMES 'utf8mb4' COLLATE 'utf8mb4_unicode_ci';
SET CHARACTER SET utf8mb4;

-- Configurar variables de sesión
SET character_set_client = utf8mb4;
SET character_set_connection = utf8mb4;
SET character_set_results = utf8mb4;
SET collation_connection = utf8mb4_unicode_ci;

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
	horario BOOLEAN DEFAULT FALSE,
	nota BOOLEAN DEFAULT FALSE,
	asistencia BOOLEAN DEFAULT FALSE,
	matricula BOOLEAN DEFAULT FALSE
);

INSERT INTO rol (nombre, solo_lectura, rol, usuario, grado, curso, materia, actividad, horario, nota, asistencia, matricula)
VALUES
('Administrador', FALSE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE),
('Coordinador', FALSE, FALSE, FALSE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE),
('Docente', FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, TRUE, FALSE, TRUE, TRUE, FALSE),
('Acudiente', TRUE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE),
('Estudiante', TRUE, FALSE, FALSE, FALSE, FALSE, FALSE, TRUE, TRUE, TRUE, FALSE, FALSE);



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
-- ADMINISTRADOR
(1, 'admin', 'admin', 'ADMIN', 'ADMIN', '2000-01-01', 25, 'M', 1234567890, 'Bogota', '3003344556', 'admin@ejemplo.com'),
-- DOCENTES
(3, 'adoe', 'teachpass123', 'Alice', 'Doe', '1980-03-10', 43, 'F', 1122334451, 'Cali', '3001122334', 'adoe@ejemplo.com'),
(3, 'bsmith', 'teachpass456', 'Bob', 'Smith', '1975-07-25', 48, 'M', 5566778892, 'Barranquilla', '3005566778', 'bsmith@ejemplo.com'),
(3, 'cgonzalez', 'teach789', 'Carlos', 'González', '1982-06-15', 41, 'M', 1122334452, 'Bogotá', '3101234567', 'cgonzalez@ejemplo.com'),
(3, 'lmartinez', 'teach321', 'Laura', 'Martínez', '1985-09-22', 38, 'F', 2233445564, 'Medellín', '3112345678', 'lmartinez@ejemplo.com'),
(3, 'prodriguez', 'teach654', 'Pedro', 'Rodríguez', '1978-12-05', 45, 'M', 3344556675, 'Cali', '3123456789', 'prodriguez@ejemplo.com'),
(3, 'mlopez', 'teach987', 'María', 'López', '1983-04-18', 40, 'F', 4455667786, 'Cartagena', '3134567890', 'mlopez@ejemplo.com'),
(3, 'jramirez', 'teach147', 'Jorge', 'Ramírez', '1981-08-30', 42, 'M', 5566778897, 'Bucaramanga', '3145678901', 'jramirez@ejemplo.com'),
(3, 'aherrera', 'teach258', 'Ana', 'Herrera', '1986-11-12', 37, 'F', 6677889908, 'Pereira', '3156789012', 'aherrera@ejemplo.com'),
(3, 'fcastro', 'teach369', 'Fernando', 'Castro', '1979-02-28', 44, 'M', 7788990019, 'Barranquilla', '3167890123', 'fcastro@ejemplo.com'),
(3, 'smoreno', 'teach741', 'Sandra', 'Moreno', '1984-07-14', 39, 'F', 8899001120, 'Manizales', '3178901234', 'smoreno@ejemplo.com'),
(3, 'rmendez', 'teach852', 'Ricardo', 'Méndez', '1980-10-25', 43, 'M', 9900112231, 'Cúcuta', '3189012345', 'rmendez@ejemplo.com'),
(3, 'nvega', 'teach963', 'Natalia', 'Vega', '1987-03-08', 36, 'F', 1011223342, 'Santa Marta', '3190123456', 'nvega@ejemplo.com'),
-- ACUDIENTES
(4, 'jgarcia', 'parent101', 'Juan', 'García', '1975-05-20', 48, 'M', 1122334453, 'Bogotá', '3201234567', 'jgarcia@ejemplo.com'),
(4, 'mfernandez', 'parent102', 'Martha', 'Fernández', '1977-08-15', 46, 'F', 2233445565, 'Medellín', '3212345678', 'mfernandez@ejemplo.com'),
(4, 'lsanchez', 'parent103', 'Luis', 'Sánchez', '1973-11-30', 50, 'M', 3344556676, 'Cali', '3223456789', 'lsanchez@ejemplo.com'),
(4, 'cdiaz', 'parent104', 'Carolina', 'Díaz', '1976-02-14', 47, 'F', 4455667787, 'Cartagena', '3234567890', 'cdiaz@ejemplo.com'),
(4, 'atorre', 'parent105', 'Andrés', 'Torres', '1974-06-22', 49, 'M', 5566778898, 'Bucaramanga', '3245678901', 'atorre@ejemplo.com'),
(4, 'pruiz', 'parent106', 'Patricia', 'Ruiz', '1978-09-10', 45, 'F', 6677889909, 'Pereira', '3256789012', 'pruiz@ejemplo.com'),
-- ESTUDIANTES PRIMERO DE PRIMARIA
(5, 'sgarcia', 'student201', 'Sofía', 'García', '2018-03-15', 6, 'F', 1001234567, 'Bogotá', '3401234567', 'sgarcia@ejemplo.com'),
(5, 'dfernandez', 'student202', 'Daniel', 'Fernández', '2018-05-20', 6, 'M', 1002345678, 'Medellín', '3402345678', 'dfernandez@ejemplo.com'),
(5, 'lusanchez', 'student203', 'Luna', 'Sánchez', '2018-07-10', 6, 'F', 1003456789, 'Cali', '3403456789', 'lusanchez@ejemplo.com'),
(5, 'mdiaz', 'student204', 'Mateo', 'Díaz', '2018-09-25', 6, 'M', 1004567890, 'Cartagena', '3404567890', 'mdiaz@ejemplo.com'),
(5, 'vrodriguez', 'student221', 'Victoria', 'Rodríguez', '2018-02-10', 6, 'F', 1021234567, 'Cali', '3421234567', 'vrodriguez@ejemplo.com'),
(5, 'eramirez', 'student222', 'Emilio', 'Ramírez', '2018-04-15', 6, 'M', 1022345678, 'Cartagena', '3422345678', 'eramirez@ejemplo.com');



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
(1, '1A'),
(1, '1B'),
(1, '1C'),

(2, '2A'),
(2, '2B'),
(2, '2C'),

(3, '3A'),
(3, '3B'),
(3, '3C'),

(4, '4A'),
(4, '4B'),
(4, '4C'),

(5, '5A'),
(5, '5B'),
(5, '5C'),

(6, '6A'),
(6, '6B'),

(7, '7A'),
(7, '7B'),

(8, '8A'),
(8, '8B'),

(9, '9A'),
(9, '9B'),

(10, '10A'),
(10, '10B'),

(11, '11A'),
(11, '11B');



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

INSERT INTO estudiante_detalle (id_usuario, id_curso, id_acudiente)
VALUES
(20, 1, 14),
(21, 1, 15),
(22, 2, 16),
(23, 2, 17),
(24, 3, 18),
(25, 3, 19);



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
(2, 'Matemáticas I', 'Números del 1-100, sumas y restas básicas'),
(3, 'Lengua Castellana I', 'Lectoescritura, vocales, consonantes y construcción de palabras'),
(8, 'Ciencias Naturales I', 'Conocimiento del entorno, seres vivos y no vivos'),
(9, 'Ciencias Sociales I', 'Familia, escuela y comunidad'),
(10, 'Educación Artística I', 'Expresión artística, dibujo y manualidades'),
(11, 'Educación Física I', 'Desarrollo motriz y juegos recreativos'),
(12, 'Inglés I', 'Vocabulario básico y frases simples en inglés');



USE grado;

CREATE TABLE IF NOT EXISTS materias_grado (
	id_materia_grado BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_grado BIGINT NOT NULL,
    id_materia BIGINT NOT NULL,
    FOREIGN KEY (id_grado) REFERENCES grado.grado(id_grado),
    FOREIGN KEY (id_materia) REFERENCES materia.materia(id_materia)
);

INSERT INTO materias_grado (id_grado, id_materia)
VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7);



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
-- ACTIVIDADES DE MATEMÁTICAS I
(1, 'Taller de Números', 'Reconocimiento y escritura de números del 1 al 100', 15),
(1, 'Quiz de Sumas', 'Evaluación de sumas básicas', 20),
(1, 'Quiz de Restas', 'Evaluación de restas básicas', 20),
(1, 'Trabajo en Clase', 'Participación y ejercicios en clase', 25),
(1, 'Examen Final', 'Evaluación final del período', 20),
-- ACTIVIDADES DE LENGUA CASTELLANA I
(2, 'Lectura en Voz Alta', 'Evaluación de lectura', 20),
(2, 'Dictado', 'Escritura al dictado', 15),
(2, 'Taller de Escritura', 'Construcción de palabras y oraciones', 25),
(2, 'Trabajo en Clase', 'Participación activa', 20),
(2, 'Evaluación Final', 'Examen de período', 20),
-- ACTIVIDADES DE CIENCIAS NATURALES I
(3, 'Proyecto de Seres Vivos', 'Investigación sobre animales o plantas', 30),
(3, 'Quiz', 'Evaluación de conocimientos', 25),
(3, 'Trabajo en Clase', 'Participación y tareas', 25),
(3, 'Evaluación Final', 'Examen del período', 20),
-- ACTIVIDADES DE CIENCIAS SOCIALES I
(4, 'Mi Familia', 'Proyecto sobre la familia', 25),
(4, 'La Escuela', 'Actividad sobre la escuela y sus espacios', 20),
(4, 'Mi Comunidad', 'Reconocimiento del entorno', 20),
(4, 'Trabajo en Clase', 'Participación y tareas', 20),
(4, 'Evaluación Final', 'Examen del período', 15),
-- ACTIVIDADES DE EDUCACIÓN ARTÍSTICA I
(5, 'Dibujo Libre', 'Expresión artística mediante dibujo', 25),
(5, 'Manualidades', 'Trabajos manuales creativos', 25),
(5, 'Proyecto Artístico', 'Creación de obra artística', 30),
(5, 'Participación en Clase', 'Creatividad y esfuerzo', 20),
-- ACTIVIDADES DE EDUCACIÓN FÍSICA I
(6, 'Coordinación Motriz', 'Evaluación de movimientos básicos', 25),
(6, 'Juegos Recreativos', 'Participación en juegos', 25),
(6, 'Trabajo en Equipo', 'Actividades grupales', 25),
(6, 'Actitud y Esfuerzo', 'Disposición y comportamiento', 25),
-- ACTIVIDADES DE INGLÉS I
(7, 'Vocabulario Básico', 'Evaluación de palabras aprendidas', 25),
(7, 'Speaking Practice', 'Práctica oral de frases simples', 25),
(7, 'Listening Activities', 'Comprensión auditiva', 20),
(7, 'Trabajo en Clase', 'Participación y tareas', 15),
(7, 'Evaluación Final', 'Examen del período', 15);



USE horario;

CREATE TABLE IF NOT EXISTS periodo_academico(
    id_periodo BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL
);

INSERT INTO periodo_academico (nombre, fecha_inicio, fecha_fin)
VALUES
('2024-1', '2024-01-15', '2024-06-15'),
('2042-2', '2024-07-01', '2024-12-15'),
('2025-1', '2025-01-15', '2025-06-15'),
('2025-2', '2025-07-01', '2025-12-15'),
('2026-1', '2026-01-15', '2026-06-15'),
('2026-2', '2026-07-01', '2026-12-15');

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

INSERT INTO horario_clase (id_grado, id_materia, dia_semana, hora_inicio, hora_fin, salon)
VALUES
-- Lunes
(1, 1, 'Lunes', '07:00:00', '08:30:00', '101'),      -- Matemáticas I
(1, 2, 'Lunes', '08:30:00', '10:00:00', '101'),      -- Lengua Castellana I
(1, 3, 'Lunes', '10:30:00', '11:30:00', '101'),      -- Ciencias Naturales I
-- Martes
(1, 2, 'Martes', '07:00:00', '08:30:00', '101'),     -- Lengua Castellana I
(1, 3, 'Martes', '08:30:00', '10:00:00', '101'),     -- Matemáticas I
(1, 6, 'Martes', '10:30:00', '11:30:00', 'Gimnasio'), -- Educación Física I
-- Miércoles
(1, 4, 'Miércoles', '07:00:00', '08:30:00', '101'),  -- Ciencias Sociales I
(1, 7, 'Miércoles', '08:30:00', '10:00:00', '101'),  -- Inglés I
(1, 5, 'Miércoles', '10:30:00', '11:30:00', 'Sala Arte'), -- Educación Artística I
-- Jueves
(1, 1, 'Jueves', '07:00:00', '08:30:00', '101'),     -- Matemáticas I
(1, 2, 'Jueves', '08:30:00', '10:00:00', '101'),     -- Lengua Castellana I
(1, 3, 'Jueves', '10:30:00', '11:30:00', '101'),     -- Ciencias Naturales I
-- Viernes
(1, 7, 'Viernes', '07:00:00', '08:30:00', '101'),    -- Inglés I
(1, 5, 'Viernes', '08:30:00', '10:00:00', 'Sala Arte'), -- Educación Artística I
(1, 6, 'Viernes', '10:30:00', '11:30:00', 'Gimnasio'); -- Educación Física I



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

INSERT INTO nota (id_actividad, id_estudiante, id_periodo, calificacion, observacion, tipo)
VALUES
-- MATEMÁTICAS I (id_materia=1, actividades 1–5)
(1, 20, 4, 4.5, 'Excelente reconocimiento de números', 'Normal'),
(2, 20, 4, 4.0, 'Buen desempeño en sumas', 'Normal'),
(3, 20, 4, 3.6, 'Debe mejorar en restas', 'Normal'),
(4, 20, 4, 4.7, 'Participa activamente en clase', 'Normal'),
(5, 20, 4, 4.3, 'Buen resultado en examen final', 'Normal'),

(1, 21, 4, 4.2, 'Buen trabajo general', 'Normal'),
(2, 21, 4, 3.9, 'Mejorar la concentración', 'Normal'),
(3, 21, 4, 3.5, 'Debe practicar restas', 'Normal'),
(4, 21, 4, 4.4, 'Participativo y responsable', 'Normal'),
(5, 21, 4, 4.0, 'Cumple con los objetivos', 'Normal'),

(1, 22, 4, 4.8, 'Excelente desempeño', 'Normal'),
(2, 22, 4, 4.6, 'Excelente comprensión', 'Normal'),
(3, 22, 4, 4.5, 'Buen trabajo en restas', 'Normal'),
(4, 22, 4, 4.9, 'Muy participativo', 'Normal'),
(5, 22, 4, 4.7, 'Excelente examen', 'Normal'),

(1, 23, 4, 4.1, 'Buen avance', 'Normal'),
(2, 23, 4, 3.8, 'Debe mejorar la atención', 'Normal'),
(3, 23, 4, 3.7, 'Cumple con lo básico', 'Normal'),
(4, 23, 4, 4.2, 'Actitud positiva en clase', 'Normal'),
(5, 23, 4, 4.0, 'Aprobado con buen esfuerzo', 'Normal'),

(1, 24, 4, 4.4, 'Buen manejo de números', 'Normal'),
(2, 24, 4, 3.9, 'A veces se distrae', 'Normal'),
(3, 24, 4, 4.1, 'Buen progreso', 'Normal'),
(4, 24, 4, 4.6, 'Participa con entusiasmo', 'Normal'),
(5, 24, 4, 4.3, 'Superó las expectativas', 'Normal'),

(1, 25, 4, 4.0, 'Buen desempeño general', 'Normal'),
(2, 25, 4, 3.7, 'Debe reforzar sumas', 'Normal'),
(3, 25, 4, 4.1, 'Satisface los objetivos', 'Normal'),
(4, 25, 4, 4.5, 'Atento y colaborador', 'Normal'),
(5, 25, 4, 4.2, 'Buen examen final', 'Normal'),
-- LENGUA CASTELLANA I (actividades 6–10)
(6, 20, 4, 4.3, 'Buena fluidez al leer', 'Normal'),
(7, 20, 4, 3.9, 'Debe mejorar ortografía', 'Normal'),
(8, 20, 4, 4.4, 'Escritura clara y coherente', 'Normal'),
(9, 20, 4, 4.2, 'Participa constantemente', 'Normal'),
(10, 20, 4, 4.1, 'Buen desempeño final', 'Normal'),

(6, 21, 4, 3.8, 'Lee con buena entonación', 'Normal'),
(7, 21, 4, 3.7, 'Errores leves en dictado', 'Normal'),
(8, 21, 4, 4.0, 'Mejora continua', 'Normal'),
(9, 21, 4, 4.2, 'Participativo', 'Normal'),
(10, 21, 4, 3.9, 'Cumple con los objetivos', 'Normal'),

(6, 22, 4, 4.7, 'Excelente lectura y comprensión', 'Normal'),
(7, 22, 4, 4.5, 'Muy buena ortografía', 'Normal'),
(8, 22, 4, 4.6, 'Excelente redacción', 'Normal'),
(9, 22, 4, 4.8, 'Participación destacada', 'Normal'),
(10, 22, 4, 4.6, 'Excelente cierre de período', 'Normal'),

(6, 23, 4, 4.0, 'Buena lectura', 'Normal'),
(7, 23, 4, 4.1, 'Dictado correcto', 'Normal'),
(8, 23, 4, 4.0, 'Cumple con lo esperado', 'Normal'),
(9, 23, 4, 4.3, 'Participación regular', 'Normal'),
(10, 23, 4, 4.0, 'Buen resultado final', 'Normal'),

(6, 24, 4, 4.2, 'Buena lectura', 'Normal'),
(7, 24, 4, 4.0, 'Dictado correcto', 'Normal'),
(8, 24, 4, 4.1, 'Cumple con lo esperado', 'Normal'),
(9, 24, 4, 4.4, 'Participa activamente', 'Normal'),
(10, 24, 4, 4.2, 'Buen resultado final', 'Normal'),

(6, 25, 4, 3.9, 'Lectura comprensible', 'Normal'),
(7, 25, 4, 3.8, 'Debe mejorar ortografía', 'Normal'),
(8, 25, 4, 4.0, 'Progreso visible', 'Normal'),
(9, 25, 4, 4.2, 'Actitud participativa', 'Normal'),
(10, 25, 4, 4.0, 'Buen desempeño', 'Normal'),
-- CIENCIAS NATURALES I (actividades 11–14)
(11, 20, 4, 4.4, 'Excelente proyecto', 'Normal'),
(12, 20, 4, 4.0, 'Buen resultado en quiz', 'Normal'),
(13, 20, 4, 4.2, 'Cumple con las tareas', 'Normal'),
(14, 20, 4, 4.3, 'Buen examen final', 'Normal'),

(11, 21, 4, 4.1, 'Proyecto completo y ordenado', 'Normal'),
(12, 21, 4, 4.0, 'Buen desempeño', 'Normal'),
(13, 21, 4, 4.1, 'Participativo', 'Normal'),
(14, 21, 4, 4.0, 'Buen cierre', 'Normal'),

(11, 22, 4, 4.8, 'Excelente trabajo', 'Normal'),
(12, 22, 4, 4.7, 'Gran conocimiento del tema', 'Normal'),
(13, 22, 4, 4.8, 'Excelente participación', 'Normal'),
(14, 22, 4, 4.9, 'Examen sobresaliente', 'Normal'),

(11, 23, 4, 4.0, 'Buen trabajo', 'Normal'),
(12, 23, 4, 3.8, 'Debe repasar', 'Normal'),
(13, 23, 4, 4.1, 'Cumple con tareas', 'Normal'),
(14, 23, 4, 4.0, 'Buen examen', 'Normal'),

(11, 24, 4, 4.3, 'Buen proyecto', 'Normal'),
(12, 24, 4, 4.0, 'Buen quiz', 'Normal'),
(13, 24, 4, 4.4, 'Participativo', 'Normal'),
(14, 24, 4, 4.2, 'Examen correcto', 'Normal'),

(11, 25, 4, 3.9, 'Cumple con el proyecto', 'Normal'),
(12, 25, 4, 3.7, 'Debe reforzar conceptos', 'Normal'),
(13, 25, 4, 4.0, 'Participa regularmente', 'Normal'),
(14, 25, 4, 3.9, 'Resultado suficiente', 'Normal'),
-- CIENCIAS SOCIALES I (actividades 15–19)
(15, 20, 4, 4.3, 'Proyecto familiar completo', 'Normal'),
(16, 20, 4, 4.0, 'Buen trabajo', 'Normal'),
(17, 20, 4, 4.1, 'Reconoce su comunidad', 'Normal'),
(18, 20, 4, 4.2, 'Participativo', 'Normal'),
(19, 20, 4, 4.0, 'Buen examen final', 'Normal'),

(15, 21, 4, 4.0, 'Cumple con el proyecto', 'Normal'),
(16, 21, 4, 3.8, 'Debe mejorar presentación', 'Normal'),
(17, 21, 4, 4.0, 'Buen conocimiento', 'Normal'),
(18, 21, 4, 4.2, 'Buena actitud', 'Normal'),
(19, 21, 4, 3.9, 'Resultado adecuado', 'Normal'),

(15, 22, 4, 4.7, 'Excelente proyecto', 'Normal'),
(16, 22, 4, 4.6, 'Muy buena presentación', 'Normal'),
(17, 22, 4, 4.8, 'Gran conocimiento del entorno', 'Normal'),
(18, 22, 4, 4.9, 'Excelente participación', 'Normal'),
(19, 22, 4, 4.7, 'Examen destacado', 'Normal'),

(15, 23, 4, 4.0, 'Buen trabajo', 'Normal'),
(16, 23, 4, 3.9, 'Debe reforzar', 'Normal'),
(17, 23, 4, 4.1, 'Cumple con el objetivo', 'Normal'),
(18, 23, 4, 4.2, 'Buena actitud', 'Normal'),
(19, 23, 4, 4.0, 'Examen correcto', 'Normal'),

(15, 24, 4, 4.2, 'Buen trabajo', 'Normal'),
(16, 24, 4, 4.0, 'Cumple', 'Normal'),
(17, 24, 4, 4.3, 'Participativo', 'Normal'),
(18, 24, 4, 4.4, 'Actitud positiva', 'Normal'),
(19, 24, 4, 4.1, 'Buen resultado', 'Normal'),

(15, 25, 4, 3.9, 'Cumple con lo básico', 'Normal'),
(16, 25, 4, 3.8, 'Debe mejorar redacción', 'Normal'),
(17, 25, 4, 4.0, 'Participa regularmente', 'Normal'),
(18, 25, 4, 4.1, 'Responsable', 'Normal'),
(19, 25, 4, 4.0, 'Buen examen final', 'Normal'),
-- EDUCACIÓN ARTÍSTICA I (actividades 20–23)
(20, 20, 4, 4.5, 'Dibujo colorido y creativo', 'Normal'),
(21, 20, 4, 4.4, 'Excelentes manualidades', 'Normal'),
(22, 20, 4, 4.6, 'Proyecto artístico sobresaliente', 'Normal'),
(23, 20, 4, 4.3, 'Participación activa', 'Normal'),

(20, 21, 4, 4.2, 'Buen trabajo', 'Normal'),
(21, 21, 4, 4.0, 'Creatividad adecuada', 'Normal'),
(22, 21, 4, 4.1, 'Buen esfuerzo', 'Normal'),
(23, 21, 4, 4.2, 'Participativo', 'Normal'),

(20, 22, 4, 4.9, 'Obra destacada', 'Normal'),
(21, 22, 4, 4.7, 'Excelente técnica', 'Normal'),
(22, 22, 4, 4.8, 'Muy creativo', 'Normal'),
(23, 22, 4, 4.9, 'Excelente disposición', 'Normal'),

(20, 23, 4, 4.1, 'Buen trabajo', 'Normal'),
(21, 23, 4, 4.0, 'Cumple con lo esperado', 'Normal'),
(22, 23, 4, 4.2, 'Buen proyecto', 'Normal'),
(23, 23, 4, 4.1, 'Participación adecuada', 'Normal'),

(20, 24, 4, 4.4, 'Buen dibujo', 'Normal'),
(21, 24, 4, 4.3, 'Manualidad limpia', 'Normal'),
(22, 24, 4, 4.5, 'Proyecto bien elaborado', 'Normal'),
(23, 24, 4, 4.2, 'Participa con entusiasmo', 'Normal'),

(20, 25, 4, 4.0, 'Dibujo correcto', 'Normal'),
(21, 25, 4, 3.9, 'Debe mejorar detalles', 'Normal'),
(22, 25, 4, 4.1, 'Buen trabajo', 'Normal'),
(23, 25, 4, 4.0, 'Participativo', 'Normal'),
-- EDUCACIÓN FÍSICA I (actividades 24–27)
(24, 20, 4, 4.4, 'Buena coordinación', 'Normal'),
(25, 20, 4, 4.2, 'Actitud excelente', 'Normal'),
(26, 20, 4, 4.3, 'Trabajo en equipo sobresaliente', 'Normal'),
(27, 20, 4, 4.5, 'Muy buena disposición', 'Normal'),

(24, 21, 4, 4.1, 'Buen rendimiento', 'Normal'),
(25, 21, 4, 4.0, 'Participativo', 'Normal'),
(26, 21, 4, 4.2, 'Colabora con el grupo', 'Normal'),
(27, 21, 4, 4.3, 'Actitud positiva', 'Normal'),

(24, 22, 4, 4.8, 'Excelente desempeño físico', 'Normal'),
(25, 22, 4, 4.6, 'Destacado en juegos', 'Normal'),
(26, 22, 4, 4.7, 'Excelente compañerismo', 'Normal'),
(27, 22, 4, 4.8, 'Gran actitud', 'Normal'),

(24, 23, 4, 4.0, 'Buen trabajo', 'Normal'),
(25, 23, 4, 3.9, 'Debe mejorar resistencia', 'Normal'),
(26, 23, 4, 4.0, 'Cumple con lo esperado', 'Normal'),
(27, 23, 4, 4.1, 'Actitud responsable', 'Normal'),

(24, 24, 4, 4.3, 'Buena coordinación', 'Normal'),
(25, 24, 4, 4.1, 'Participativo', 'Normal'),
(26, 24, 4, 4.2, 'Buen trabajo grupal', 'Normal'),
(27, 24, 4, 4.4, 'Excelente esfuerzo', 'Normal'),

(24, 25, 4, 4.0, 'Buen rendimiento', 'Normal'),
(25, 25, 4, 3.8, 'Debe mejorar velocidad', 'Normal'),
(26, 25, 4, 4.1, 'Cumple con el objetivo', 'Normal'),
(27, 25, 4, 4.0, 'Buena actitud', 'Normal'),
-- INGLÉS I (actividades 28–32)
(28, 20, 4, 4.5, 'Excelente vocabulario', 'Normal'),
(29, 20, 4, 4.3, 'Buena pronunciación', 'Normal'),
(30, 20, 4, 4.0, 'Entiende bien las instrucciones', 'Normal'),
(31, 20, 4, 4.2, 'Participa en clase', 'Normal'),
(32, 20, 4, 4.3, 'Buen examen final', 'Normal'),

(28, 21, 4, 4.1, 'Buen vocabulario', 'Normal'),
(29, 21, 4, 4.0, 'Pronunciación aceptable', 'Normal'),
(30, 21, 4, 3.8, 'Debe mejorar listening', 'Normal'),
(31, 21, 4, 4.2, 'Participativo', 'Normal'),
(32, 21, 4, 4.0, 'Aprobado', 'Normal'),

(28, 22, 4, 4.9, 'Excelente dominio del vocabulario', 'Normal'),
(29, 22, 4, 4.7, 'Excelente speaking', 'Normal'),
(30, 22, 4, 4.8, 'Gran comprensión auditiva', 'Normal'),
(31, 22, 4, 4.9, 'Excelente participación', 'Normal'),
(32, 22, 4, 4.7, 'Examen sobresaliente', 'Normal'),

(28, 23, 4, 4.2, 'Buen vocabulario', 'Normal'),
(29, 23, 4, 4.1, 'Buena pronunciación', 'Normal'),
(30, 23, 4, 4.0, 'Cumple con listening', 'Normal'),
(31, 23, 4, 4.3, 'Actitud participativa', 'Normal'),
(32, 23, 4, 4.1, 'Buen cierre de período', 'Normal'),

(28, 24, 4, 4.3, 'Buen vocabulario', 'Normal'),
(29, 24, 4, 4.2, 'Pronunciación clara', 'Normal'),
(30, 24, 4, 4.1, 'Comprensión adecuada', 'Normal'),
(31, 24, 4, 4.4, 'Participación constante', 'Normal'),
(32, 24, 4, 4.3, 'Buen examen final', 'Normal'),

(28, 25, 4, 4.0, 'Buen vocabulario', 'Normal'),
(29, 25, 4, 3.9, 'Debe mejorar pronunciación', 'Normal'),
(30, 25, 4, 3.8, 'Comprende lo básico', 'Normal'),
(31, 25, 4, 4.1, 'Buena actitud', 'Normal'),
(32, 25, 4, 4.0, 'Buen examen', 'Normal');

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

INSERT INTO asistencia (id_estudiante, id_clase, estado, observacion)
VALUES
-- LUNES (Clases 1–3)
(20, 1, 'Presente', NULL),
(20, 2, 'Presente', NULL),
(20, 3, 'Presente', NULL),

(21, 1, 'Presente', NULL),
(21, 2, 'Ausente', 'Falta sin excusa'),
(21, 3, 'Presente', NULL),

(22, 1, 'Tarde', 'Llegó 10 minutos tarde'),
(22, 2, 'Presente', NULL),
(22, 3, 'Presente', NULL),

(23, 1, 'Presente', NULL),
(23, 2, 'Presente', NULL),
(23, 3, 'Presente', NULL),

(24, 1, 'Excusado', 'Permiso médico'),
(24, 2, 'Excusado', 'Permiso médico'),
(24, 3, 'Presente', NULL),

(25, 1, 'Presente', NULL),
(25, 2, 'Presente', NULL),
(25, 3, 'Presente', NULL),
-- MARTES (Clases 4–6)
(20, 4, 'Presente', NULL),
(20, 5, 'Presente', NULL),
(20, 6, 'Presente', NULL),

(21, 4, 'Presente', NULL),
(21, 5, 'Presente', NULL),
(21, 6, 'Presente', NULL),

(22, 4, 'Presente', NULL),
(22, 5, 'Ausente', 'No asistió a clase'),
(22, 6, 'Presente', NULL),

(23, 4, 'Presente', NULL),
(23, 5, 'Presente', NULL),
(23, 6, 'Presente', NULL),

(24, 4, 'Tarde', 'Llegó 5 minutos tarde'),
(24, 5, 'Presente', NULL),
(24, 6, 'Presente', NULL),

(25, 4, 'Presente', NULL),
(25, 5, 'Presente', NULL),
(25, 6, 'Presente', NULL),
-- MIÉRCOLES (Clases 7–9)
(20, 7, 'Presente', NULL),
(20, 8, 'Presente', NULL),
(20, 9, 'Presente', NULL),

(21, 7, 'Presente', NULL),
(21, 8, 'Presente', NULL),
(21, 9, 'Presente', NULL),

(22, 7, 'Ausente', 'Falta justificada'),
(22, 8, 'Excusado', 'Permiso médico'),
(22, 9, 'Excusado', 'Permiso médico'),

(23, 7, 'Presente', NULL),
(23, 8, 'Presente', NULL),
(23, 9, 'Presente', NULL),

(24, 7, 'Presente', NULL),
(24, 8, 'Presente', NULL),
(24, 9, 'Presente', NULL),

(25, 7, 'Presente', NULL),
(25, 8, 'Presente', NULL),
(25, 9, 'Presente', NULL),
-- JUEVES (Clases 10–12)
(20, 10, 'Presente', NULL),
(20, 11, 'Presente', NULL),
(20, 12, 'Presente', NULL),

(21, 10, 'Presente', NULL),
(21, 11, 'Tarde', 'Llegó 15 minutos tarde'),
(21, 12, 'Presente', NULL),

(22, 10, 'Presente', NULL),
(22, 11, 'Presente', NULL),
(22, 12, 'Presente', NULL),

(23, 10, 'Presente', NULL),
(23, 11, 'Presente', NULL),
(23, 12, 'Presente', NULL),

(24, 10, 'Presente', NULL),
(24, 11, 'Presente', NULL),
(24, 12, 'Ausente', 'Falta sin justificar'),

(25, 10, 'Presente', NULL),
(25, 11, 'Presente', NULL),
(25, 12, 'Presente', NULL),
-- VIERNES (Clases 13–15)
(20, 13, 'Presente', NULL),
(20, 14, 'Presente', NULL),
(20, 15, 'Presente', NULL),

(21, 13, 'Presente', NULL),
(21, 14, 'Presente', NULL),
(21, 15, 'Presente', NULL),

(22, 13, 'Presente', NULL),
(22, 14, 'Presente', NULL),
(22, 15, 'Presente', NULL),

(23, 13, 'Presente', NULL),
(23, 14, 'Ausente', 'No se presentó a clase'),
(23, 15, 'Presente', NULL),

(24, 13, 'Presente', NULL),
(24, 14, 'Presente', NULL),
(24, 15, 'Presente', NULL),

(25, 13, 'Tarde', 'Llegó al final de la clase'),
(25, 14, 'Presente', NULL),
(25, 15, 'Presente', NULL);



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

INSERT INTO matricula (id_estudiante, id_periodo, fecha_matricula, estado)
VALUES
(20, 4, '2025-06-22', 'Activa'),
(21, 4, '2025-06-21', 'Activa'),
(22, 4, '2025-06-19', 'Activa'),
(23, 4, '2025-06-19', 'Activa'),
(24, 4, '2025-06-22', 'Activa'),
(25, 4, '2025-06-22', 'Activa');