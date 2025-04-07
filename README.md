# IngenieriaSoftwareProyecto_SistemaMedico

## Descripción
Este proyecto es una aplicación de autenticación de usuarios desarrollada con Spring Boot. Actualmente, cuenta con plantillas funcionales para el login y el registro de usuarios.

## Documentación
La documentación de este documento así como las plantillas UML se encuentran en las carpetas "Documentos" y "UML"

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

## Instalación y Ejecución con XAMPP
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

### Modo Docker
1. Asegurarse de que XAMPP esté apagado (para evitar conflictos en el puerto MySQL).
2. Ejecutar el siguiente comando en la terminal:
   ```sh
   docker-compose up --build
   ```

## Funcionalidades Implementadas
- Plantillas HTML para Login y Registro (Thymeleaf)
- Configuración básica de Spring Boot
- Implementación de bae de datos
- Ejecución con Docker
- Administración de roles
- Implementación de cambio de tema claro y oscuro

![image](https://github.com/user-attachments/assets/2628ac31-4c0d-46b8-b62e-f40ab087cab1)
![image](https://github.com/user-attachments/assets/e8ac864c-ffe1-43f2-ba17-1e6e6e14a5d8)
![image](https://github.com/user-attachments/assets/35bc81fd-742b-49a4-9a6f-dd130af96766)
![image](https://github.com/user-attachments/assets/58c3de12-9dce-4e3a-9f4d-20f818b93b3e)




## Próximos Pasos
- Implementar autenticación segura con JWT o sesiones
- Manejo de roles y permisos
- Registro exitoso con la base de datos
- Correción de errores con controladores

