FROM gradle:8.10-jdk23 AS build
WORKDIR /app
COPY . .
RUN gradle build -x test

FROM openjdk:23-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8072
ENTRYPOINT ["java", "-jar", "app.jar"]