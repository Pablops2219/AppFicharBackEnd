# Imagen base ligera de Java 17
FROM eclipse-temurin:17-jdk-alpine

# Carpeta dentro del contenedor donde se copiará la app
WORKDIR /app

# Copiamos el .jar desde tu máquina local (está en /target)
COPY target/fichar-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto por donde correrá la app
EXPOSE 8080

# Comando para lanzar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
