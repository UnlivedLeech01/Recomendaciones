# IngenieriaSoftwareProyecto_SistemaMedico

## Descripción
Este proyecto es una aplicación de autenticación de usuarios desarrollada con Spring Boot. Actualmente, cuenta con plantillas funcionales para el login y el registro de usuarios.

## Tecnologías Utilizadas
- **Java:** 21 (Corretto)
- **Maven:** 3.9.9
- **Spring Boot:** Última versión compatible con Java 21
- **XAMPP:** Para ejecutar el servidor en el puerto 8080 (sin base de datos por ahora)

## Requisitos Previos
Antes de ejecutar el proyecto, asegúrate de tener instalado lo siguiente:
- [Java 21 (Corretto)](https://docs.aws.amazon.com/corretto/latest/corretto-21-ug/downloads-list.html)
- [Maven 3.9.9](https://maven.apache.org/download.cgi)
- [XAMPP](https://www.apachefriends.org/es/index.html) (con Apache corriendo en el puerto 8080)

Además, debes asegurarte de que XAMPP esté ejecutándose antes de iniciar la aplicación.

## Instalación y Ejecución
### 1. Clonar el Repositorio
   ```bash
   git clone https://github.com/tu-usuario/tu-repositorio.git
   cd tu-repositorio
   ```

### 2. Iniciar XAMPP
   - Abre XAMPP y asegúrate de que el servidor Apache esté en ejecución en el puerto 8080.

### 3. Ejecutar la Aplicación
   - En la terminal, dentro del directorio del proyecto, ejecuta:
   ```bash
   mvn spring-boot:run
   ```
   
### 4. Acceder a la Aplicación
   - Abre tu navegador y ve a:
   ```
   http://localhost:8080
   ```

## Funcionalidades Implementadas
- Plantillas HTML para Login y Registro (Thymeleaf)
- Configuración básica de Spring Boot

## Próximos Pasos
- Integrar base de datos MySQL con Spring Data JPA
- Implementar autenticación segura con JWT o sesiones
- Manejo de roles y permisos

