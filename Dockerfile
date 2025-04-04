# Etapa de construcción
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copiar el archivo pom.xml y descargar dependencias
COPY pom.xml /app/
RUN mvn dependency:go-offline

# Copiar el código fuente
COPY src /app/src

# Compilar la aplicación (sin ejecutar pruebas)
RUN mvn package -DskipTests

# Etapa de ejecución con imagen liviana
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copiar el jar generado desde la etapa anterior
COPY --from=builder /app/target/SistemaMedico-0.0.1-SNAPSHOT.jar /app/app.jar

# Exponer el puerto de la app
EXPOSE 8080

# Comando para ejecutar el jar
CMD ["java", "-jar", "app.jar"]
