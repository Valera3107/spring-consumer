FROM openjdk:latest

ADD target/demo-0.0.1-SNAPSHOT.jar app-consumer.jar

ENTRYPOINT ["java", "-jar", "/app-consumer.jar"]

EXPOSE 8080