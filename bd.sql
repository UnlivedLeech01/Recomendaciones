-- Eliminar la base de datos "SistemaMedico" si ya existe
DROP DATABASE IF EXISTS SistemaMedico;

-- Crear la base de datos "SistemaMedico" con codificación UTF-8
CREATE DATABASE SistemaMedico CHARACTER SET utf8 COLLATE utf8_general_ci;

-- Usar la base de datos "SistemaMedico"
USE SistemaMedico;

-- Crear la tabla de usuarios
CREATE TABLE usuarios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    autenticacion_dos_factores TINYINT DEFAULT 0
);

-- Crear la tabla de roles (Medico, Administrador, Paciente)
CREATE TABLE roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(64) NOT NULL UNIQUE
);

-- Tabla intermedia para relación muchos a muchos entre usuarios y roles
CREATE TABLE usuario_roles (
    usuario_id BIGINT,
    rol_id BIGINT,
    PRIMARY KEY (usuario_id, rol_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (rol_id) REFERENCES roles(id)
);

-- Crear la tabla de médicos
CREATE TABLE medicos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT UNIQUE,
    especialidad VARCHAR(100),
    licencia_medica VARCHAR(50) NOT NULL UNIQUE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Crear la tabla de pacientes
CREATE TABLE pacientes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT UNIQUE,
    fecha_nacimiento DATE,
    genero ENUM('Masculino','Femenino','Otro'),
    direccion VARCHAR(255),
    telefono VARCHAR(20),
    correo_contacto VARCHAR(100),
    contacto_emergencia VARCHAR(100),
    telefono_emergencia VARCHAR(20),
    enfermedades_cronicas TEXT,
    alergias TEXT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Crear la tabla de consultas
CREATE TABLE consultas (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    paciente_id BIGINT,
    medico_id BIGINT,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    sintomas TEXT,
    medicamentos TEXT,
    tratamiento TEXT,
    fecha_proxima_consulta DATE,
    consentimiento_pdf LONGBLOB,
    FOREIGN KEY (paciente_id) REFERENCES pacientes(id) ON DELETE CASCADE,
    FOREIGN KEY (medico_id) REFERENCES medicos(id) ON DELETE CASCADE
);

-- Crear la tabla de tickets
CREATE TABLE tickets (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT,
    consulta_id BIGINT,
    descripcion TEXT,
    estado ENUM('Abierto','En Proceso','Cerrado') DEFAULT 'Abierto',
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (consulta_id) REFERENCES consultas(id) ON DELETE SET NULL
);

-- Crear la tabla de mensajes
CREATE TABLE mensajes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    remitente_id BIGINT,
    destinatario_id BIGINT,
    mensaje TEXT,
    fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (remitente_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (destinatario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Crear la tabla de avisos
CREATE TABLE avisos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(255),
    mensaje TEXT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    creado_por BIGINT,
    FOREIGN KEY (creado_por) REFERENCES usuarios(id) ON DELETE SET NULL
);

-- Crear la tabla de solicitudes
CREATE TABLE solicitudes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT,
    tipo ENUM('Ausencia','Consulta'),
    estado ENUM('Pendiente','Aprobado','Rechazado') DEFAULT 'Pendiente',
    fecha_inicio DATE,
    fecha_fin DATE,
    descripcion TEXT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Crear la tabla de reportes de auditoría
CREATE TABLE reportesauditoria (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT,
    accion TEXT,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE SET NULL
);

-- Insertar roles en la tabla roles
INSERT INTO roles (nombre) VALUES ('Medico'), ('Administrador'), ('Paciente');

-- Eliminar el usuario 'admin' si ya existe
DROP USER IF EXISTS 'admin'@'localhost';
FLUSH PRIVILEGES;

-- Crear el usuario 'admin' con la contraseña 'admin'
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';

-- Otorgar todos los permisos sobre la base de datos "SistemaMedico" al usuario 'admin'
GRANT ALL PRIVILEGES ON SistemaMedico.* TO 'admin'@'localhost';

-- Aplicar los cambios
FLUSH PRIVILEGES;
