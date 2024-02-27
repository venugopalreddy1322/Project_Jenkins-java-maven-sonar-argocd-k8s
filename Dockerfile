# Used lightweight image required to run application.
FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /app
# Simply the artifact path

COPY **/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
