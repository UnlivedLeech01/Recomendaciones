# Etapa de construcción
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml . 
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/src/main/resources/templates ./src/main/resources/templates
COPY --from=build /app/src/main/resources/static ./src/main/resources/static
COPY --from=build /app/target/Recomendaciones-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
