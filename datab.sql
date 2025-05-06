-- Eliminar la base de datos "SistemaMedico" si ya existe
DROP DATABASE IF EXISTS SistemaMedico;

-- Crear la base de datos "SistemaMedico" con codificación UTF-8
CREATE DATABASE SistemaMedico CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Usar la base de datos "SistemaMedico"
USE SistemaMedico;

-- Crear la tabla de usuarios (base común para todos los tipos de usuario)
CREATE TABLE usuarios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE
);

-- Crear la tabla de roles
CREATE TABLE roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(64) NOT NULL UNIQUE,
    descripcion VARCHAR(128)
);

-- Crear la tabla intermedia para la relación muchos a muchos entre usuarios y roles
CREATE TABLE usuario_roles (
    usuario_id BIGINT,
    rol_id BIGINT,
    PRIMARY KEY (usuario_id, rol_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (rol_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Crear tabla específica para administradores
CREATE TABLE administradores (
    usuario_id BIGINT PRIMARY KEY,
    usuario VARCHAR(64) NOT NULL UNIQUE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Crear tabla específica para médicos
CREATE TABLE medicos (
    usuario_id BIGINT PRIMARY KEY,
    nombre VARCHAR(64) NOT NULL,
    apellidos VARCHAR(64) NOT NULL,
    licencia_medica VARCHAR(64) NOT NULL UNIQUE,
    especialidad VARCHAR(64) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Crear tabla específica para pacientes
CREATE TABLE pacientes (
    usuario_id BIGINT PRIMARY KEY,
    nombre VARCHAR(64) NOT NULL,
    apellidos VARCHAR(64) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    genero ENUM('Masculino', 'Femenino', 'Otro') NOT NULL,
    direccion VARCHAR(128) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    contacto_emergencia VARCHAR(128) NOT NULL,
    telefono_emergencia VARCHAR(20) NOT NULL,
    enfermedades_cronicas TEXT,
    alergias TEXT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Crear tabla de citas médicas
CREATE TABLE citas (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    paciente_id BIGINT NOT NULL,
    medico_id BIGINT NOT NULL,
    fecha_hora DATETIME NOT NULL,
    motivo TEXT,
    estado ENUM('pendiente', 'completada', 'cancelada') DEFAULT 'pendiente',
    notas TEXT,
    FOREIGN KEY (paciente_id) REFERENCES pacientes(usuario_id),
    FOREIGN KEY (medico_id) REFERENCES medicos(usuario_id)
);

-- Crear tabla de historial médico
CREATE TABLE historial_medico (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    paciente_id BIGINT NOT NULL,
    medico_id BIGINT,
    fecha_consulta DATETIME DEFAULT CURRENT_TIMESTAMP,
    diagnostico TEXT,
    tratamiento TEXT,
    notas TEXT,
    FOREIGN KEY (paciente_id) REFERENCES pacientes(usuario_id),
    FOREIGN KEY (medico_id) REFERENCES medicos(usuario_id)
);

-- Insertar roles básicos en la tabla roles
INSERT INTO roles (nombre, descripcion) VALUES 
('ROLE_ADMIN', 'Administrador del sistema con todos los permisos'),
('ROLE_MEDICO', 'Médico que puede atender pacientes y generar diagnósticos'),
('ROLE_PACIENTE', 'Paciente que puede solicitar citas y ver su historial');

-- Eliminar el usuario 'admin' si ya existe
DROP USER IF EXISTS 'admin'@'localhost';
FLUSH PRIVILEGES;

-- Crear el usuario 'admin' con la contraseña 'admin'
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';

-- Otorgar todos los permisos sobre la base de datos "SistemaMedico" al usuario 'admin'
GRANT ALL PRIVILEGES ON SistemaMedico.* TO 'admin'@'localhost';

-- Aplicar los cambios
FLUSH PRIVILEGES;