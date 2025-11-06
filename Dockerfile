# Stage 1: Build the application
FROM maven:3.9-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy only the pom.xml first (to cache dependencies)
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Now copy the source code
COPY src ./src

# Build the app (skip tests for faster builds)
RUN mvn clean package -DskipTests

# Stage 2: Runtime image
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Install curl for healthcheck (Alpine doesn't have wget by default)
RUN apk add --no-cache curl

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=docker

ENTRYPOINT ["java", "-jar", "app.jar"]