FROM maven:3.9.11-eclipse-temurin-25 AS build

# ~/app
WORKDIR /app

# ~/app/pom.xml
COPY pom.xml .

# ~/app/src/
COPY src ./src

RUN mvn clean package -DskipTests


FROM eclipse-temurin:25-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]