FROM eclipse-temurin:21-jdk-jammy
LABEL authors="Danon"

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src

RUN chmod +x mvnw

RUN ./mvnw package -DskipTests

EXPOSE 8020

ENTRYPOINT ["java", "-jar", "target/LibraryBoot-0.0.1-SNAPSHOT.jar"]