##Build
#FROM maven:3.9-eclipse-temurin-17-alpine AS build
#COPY src/main/java /home/app/src/main/java
#COPY src/main/resources /home/app/src/main/resources
#COPY pom.xml /home/app
#RUN mvn -f /home/app/pom.xml clean package -DskipTests
#
##Run
#FROM openjdk:17-alpine
#
#COPY --from=build /home/app/target/*.jar /usr/local/lib/app.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]
#

FROM openjdk:17-alpine

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve

COPY src/main/java ./src/main/java
COPY src/main/resources ./src/main/resources

CMD ["./mvnw", "spring-boot:run"]