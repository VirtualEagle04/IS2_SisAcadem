FROM openjdk:25-jdk-slim AS build

# ~/app
WORKDIR /app

# ~/app/pom.xml
COPY pom.xml .

# ~/app/src/
COPY src ./src

RUN apt-get update && apt-get install -y maven

RUN mvn clean package -DskipTests


FROM openjdk:25-jdk-slim

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "app.jar"]