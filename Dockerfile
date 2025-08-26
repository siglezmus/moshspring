# Use a valid Gradle image to build the app
FROM gradle:8.3-jdk17 AS builder

WORKDIR /app

COPY build.gradle settings.gradle gradle* ./
COPY gradle ./gradle

RUN gradle clean build -x test --no-daemon

COPY src ./src

RUN gradle clean build -x test --no-daemon

FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY --from=builder /app/build/libs/course-1.0.0.jar ./course.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "course.jar"]
