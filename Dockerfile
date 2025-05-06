FROM eclipse-temurin:21-jdk-jammy
LABEL authors="lipton"

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]