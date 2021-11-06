
FROM openjdk:8-jdk-alpine
EXPOSE 8093
ARG JAR_FILE=target/Timesheet-spring-boot-core-data-jpa-mvc-REST-1-0.0.1-SNAPSHOT.war
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]