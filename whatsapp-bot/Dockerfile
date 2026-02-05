FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY .. .
RUN mvn -pl whatsapp-bot -am -B clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/whatsapp-bot/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]