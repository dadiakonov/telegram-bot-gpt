# Build stage
FROM amazoncorretto:17 AS build
WORKDIR /app
COPY ../../ /app
RUN ./gradlew clean build --no-daemon --warning-mode all

# Runtime stage
FROM amazoncorretto:17 AS runtime
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]
