CREATE DATABASE IF NOT EXISTS estudiante;

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
    telefono VARCHAR(20)
);

INSERT INTO estudiante (id_curso, id_acudiente, usuario, clave, nombres, apellidos, fecha_nacimiento, edad, sexo, doc_identidad, ciudad_nacimiento, telefono) 
VALUES 
(1, 1, 'jdoe', 'password123', 'John', 'Doe', '2005-05-15', 18, 'M', 123456789, 'Bogotá', '3001234567'),
(1, 2, 'msmith', 'password456', 'Maria', 'Smith', '2006-08-20', 17, 'F', 987654321, 'Medellín', '3009876543');