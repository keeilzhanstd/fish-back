FROM openjdk:17-oracle
COPY target/*.jar app.jar
ARG SERVER_PORT
ENV SERVER_PORT=$SERVER_PORT
EXPOSE $SERVER_PORT
ENTRYPOINT ["java","-jar","/app.jar"]
