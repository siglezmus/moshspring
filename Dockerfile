# Use OpenJDK 17 image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy Gradle wrapper and project files
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .
COPY src ./src

# Give gradlew execution permissions
RUN chmod +x ./gradlew

# Build the Spring Boot JAR
RUN ./gradlew bootJar -x test

# Expose the port Railway provides
ENV PORT=8080
EXPOSE 8080

# Run the Spring Boot app
CMD ["java", "-jar", "build/libs/course-1.0.0.jar"]
