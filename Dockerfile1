# Build Stage
#FROM adoptopenjdk/openjdk11:alpine AS builder
FROM maven:3.8.3-openjdk-11 AS build
WORKDIR /app

COPY pom.xml .

RUN mvn clean install -DskipTests

# Clean up Maven dependencies
RUN rm -rf ~/.m2

COPY src src

# Runtime Stage
FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /app

# Create a non-root user
#RUN adduser -D myuser
#USER myuser

# Copy necessary artifacts from the builder stage
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

