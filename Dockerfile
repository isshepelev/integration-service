FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml first to cache dependencies
COPY pom.xml .
#RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application (skip tests as they require databases)
# This will trigger:
# - protobuf-maven-plugin: generates Java classes from .proto files
# - maven-compiler-plugin: generates MapStruct implementations and processes Lombok annotations
# - jaxb2-maven-plugin: generates SOAP classes from XSD
RUN mvn -B clean package -DskipTests

FROM eclipse-temurin:17-jre

ENV JAVA_OPTS="-Xmx512m"
WORKDIR /app

COPY --from=build /app/target/integration-service-0.0.1-SNAPSHOT.jar app.jar

# HTTP port
EXPOSE 8080
# gRPC port
EXPOSE 9090

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
