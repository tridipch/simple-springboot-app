FROM openjdk:8-jdk-alpine
MAINTAINER tridip
COPY target/simple-springboot-app-0.0.1-SNAPSHOT.jar simple-springboot-app.jar
ENTRYPOINT ["java","-jar","/simple-springboot-app.jar"]