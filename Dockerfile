FROM openjdk:21-jdk
ARG JAR_FILE=target/fichar-0.0.1.jar
COPY ${JAR_FILE} fichar-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "fichar-0.0.1.jar"]