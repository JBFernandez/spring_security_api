FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/ApiDemo-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_apidemo.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "app_apidemo.jar" ]