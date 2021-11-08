
FROM openjdk:8-jre-alpine
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \JAVA_OPTS=""
WORKDIR /app
ADD target/*.war app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar"]