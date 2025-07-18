-- Eliminar la base de datos "tarea2" si ya existe
DROP DATABASE IF EXISTS tarea2;

-- Crear la base de datos "tarea2" con codificación UTF-8
CREATE DATABASE tarea2 CHARACTER SET utf8 COLLATE utf8_general_ci;

-- Usar la base de datos "tarea2"
USE tarea2;

-- Crear la tabla de usuarios
CREATE TABLE usuarios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(64) NOT NULL,
    email VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL
);

-- Crear la tabla de roles
CREATE TABLE roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(64) NOT NULL UNIQUE
);

-- Crear la tabla intermedia para la relación muchos a muchos entre usuarios y roles
CREATE TABLE usuario_roles (
    usuario_id BIGINT,
    rol_id BIGINT,
    PRIMARY KEY (usuario_id, rol_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (rol_id) REFERENCES roles(id)
);

-- Insertar roles en la tabla roles
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN'), ('ROLE_USER');
