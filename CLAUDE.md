# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Integration Service is a Spring Boot 4.0.1 microservice built with Java 17 and Maven. It uses a dual-database architecture with PostgreSQL (via Spring Data JPA) for relational data and MongoDB (via Spring Data MongoDB) for document storage.

## Build and Development Commands

```bash
# Build
./mvnw clean package

# Run application
./mvnw spring-boot:run

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=IntegrationServiceApplicationTests

# Run a single test method
./mvnw test -Dtest=IntegrationServiceApplicationTests#contextLoads

# Compile only
./mvnw compile

# Build Docker image
./mvnw spring-boot:build-image
```

## Architecture

- **Entry point**: `IntegrationServiceApplication.java` - main Spring Boot class
- **Package base**: `ppr.ru.integrationservice`
- **Configuration**: `src/main/resources/application.yaml`

### Layer Organization

When adding new features, follow standard Spring Boot layering:
- `controller/` - REST API endpoints
- `service/` - Business logic
- `repository/` - Data access (JPA for PostgreSQL, MongoRepository for MongoDB)
- `model/` or `entity/` - Domain objects

### Database Strategy

- Use PostgreSQL/JPA for transactional, relational data
- Use MongoDB for flexible document-based data
- Both Spring Data starters are included and ready to configure

## Testing

- JUnit 5 (Jupiter) with Spring Boot Test support
- Test base includes starters for both JPA and MongoDB integration testing
- Tests located in `src/test/java/ppr/ru/integrationservice/`
