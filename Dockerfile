FROM maven:3-openjdk-18 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-oracle
COPY --from=build target/*.jar app.jar
ARG SERVER_PORT
ENV SERVER_PORT=$SERVER_PORT
EXPOSE $SERVER_PORT
ENTRYPOINT ["java","-jar","/app.jar"]