# === Build Stage ===
FROM maven:3.9.5-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar todo lo necesario para usar el wrapper correctamente
COPY .mvn/ .mvn/
COPY mvnw mvnw
COPY pom.xml pom.xml

# Descargar dependencias primero (cache-friendly)
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Luego copiar el código fuente
COPY src/ src/

# Build sin tests
RUN ./mvnw clean package -DskipTests

# === Runtime Stage ===
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copiar el .jar generado desde el stage anterior
COPY --from=build /app/target/*.jar app.jar

# Puerto expuesto (ajústalo si tu app usa otro)
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]


