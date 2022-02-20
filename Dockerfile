FROM maven:3-openjdk-11 AS builder
WORKDIR /project
COPY pom.xml /project/pom.xml
RUN mvn clean validate
COPY . /project/
RUN mvn clean package
FROM adoptopenjdk/openjdk11:alpine-jre
COPY --from=builder /project/target/local-docker-sync-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar"]
