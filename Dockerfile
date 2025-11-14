# ---- Build stage ----
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
COPY src ./src
# build + repackage spring-boot jar
RUN mvn -q -DskipTests clean package spring-boot:repackage

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /workspace/target/gameflix-auth.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
