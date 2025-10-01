# Stage 1: Build the application using a Maven image
FROM maven:3.9-eclipse-temurin-17 AS build

# 1. Set the working directory
WORKDIR /app

# 2. Copy the root POM first
COPY pom.xml .

# 3. Copy the api-gateway module files
COPY api-gateway/pom.xml api-gateway/
COPY api-gateway/src api-gateway/src/

# 4. Create a temporary pom.xml for building only the api-gateway
WORKDIR /app/api-gateway

# 5. Install the parent POM to local repository
RUN mvn install -N -f /app/pom.xml

# 6. Build only the api-gateway module
RUN mvn clean package -DskipTests

# Stage 2: Create the final, lightweight production image
FROM eclipse-temurin:17-jre-focal

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/api-gateway/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]