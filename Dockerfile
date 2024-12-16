# Use an official Maven image as the base image
FROM maven:3.8.3-openjdk-17-slim AS build
# Set the working directory in the container
WORKDIR /app
# Copy the pom.xml and the project files to the container
COPY pom.xml .
COPY src ./src
# Build the application using Maven
RUN mvn clean package -DskipTests
# Use an official OpenJDK image as the base image
FROM openjdk:17-alpine 

# Set the working directory in the container
WORKDIR /app
# Copy the built JAR file from the previous stage to the container
COPY --from=build /app/target/cambiar-esto-0.0.1-SNAPSHOT.war app.war
# Set the command to run the application
CMD ["java", "-jar", "app.war"]