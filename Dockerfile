# Stage 1: Build the application
FROM maven:3.9-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy only the pom.xml first (to cache dependencies)
COPY pom.xml .

# Use local Maven repository cache (optional but very effective)
# This will speed up dependency downloads on rebuilds
VOLUME /root/.m2

# Download dependencies
RUN mvn dependency:go-offline -B

# Now copy the source code
COPY src ./src

# Build the app (skip tests for faster builds)
RUN mvn clean package -DskipTests

# Stage 2: Runtime image
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=docker

ENTRYPOINT ["java", "-jar", "app.jar"]
