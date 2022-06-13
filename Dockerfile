FROM openjdk:11
COPY app.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]