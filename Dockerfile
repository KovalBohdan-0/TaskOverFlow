FROM openjdk:17-alpine

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve

COPY src/main/java ./src/main/java
COPY src/main/resources ./src/main/resources

CMD ["./mvnw", "spring-boot:run"]