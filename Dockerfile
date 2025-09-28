# Этап 1: Сборка (builder)
FROM gradle:jdk21-jammy as build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN ./gradlew assemble --no-daemon

# Package
FROM openjdk:21-jdk-slim

RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/test_kuber-1.3.4.jar

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "/app/test_kuber-1.3.4.jar"]