FROM maven:3.9.4-eclipse-temurin-21 AS builder
COPY pom.xml ./
COPY src ./src
RUN mvn clean package -DskipTests
FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=builder /target/*.jar ./app.jar
CMD ["java", "-jar", "./app.jar"]