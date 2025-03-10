# Etapa 1: Construcción de la aplicación
FROM maven:3.9.5-eclipse-temurin-21 AS build
ARG JAR_FILE=target/RetailFlow-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_RetailFlow.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_RetailFlow.jar"]