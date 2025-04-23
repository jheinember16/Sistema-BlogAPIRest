# Usar imagen base
FROM openjdk:17-jdk-slim

# Argumento para el nombre del JAR
ARG JAR_FILE=target/Sistema-BlogAPIRest-0.0.1-SNAPSHOT.jar

# Directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR usando el argumento
COPY ${JAR_FILE} app.jar

# Exponer puerto
EXPOSE 8080

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "app.jar"]
