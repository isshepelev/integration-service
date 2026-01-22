# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Integration Service — Spring Boot 4.0.1 микросервис (Java 17, Maven), предоставляющий единый API для работы с продуктами, клиентами и заказами через четыре протокола: REST, GraphQL, gRPC и SOAP.

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

# Docker
docker-compose up -d
docker compose build --no-cache && docker compose up -d  # rebuild
```

## Architecture

**Package base**: `ppr.ru.integrationservice`

### Multi-Protocol API

| Протокол | Endpoint | Схема/Контракт |
|----------|----------|----------------|
| REST | `/api/products`, `/api/customers`, `/api/orders` | OpenAPI (`/swagger-ui.html`) |
| GraphQL | `/graphql` (GraphiQL: `/graphiql`) | `src/main/resources/graphql/schema.graphqls` |
| gRPC | порт `9090` | `src/main/proto/*.proto` |
| SOAP | `/ws` | `src/main/resources/xsd/*.xsd` |

### Layer Organization

```
controller/rest/    — REST контроллеры
graphql/            — GraphQL контроллеры (@QueryMapping, @MutationMapping)
grpc/               — gRPC сервисы (extends *Grpc.*.ImplBase)
soap/endpoint/      — SOAP эндпоинты (@Endpoint)
service/            — бизнес-логика (интерфейсы)
service/impl/       — реализации сервисов
dto/                — DTO и MapStruct маперы
model/jpa/          — JPA сущности (PostgreSQL)
model/mongo/        — MongoDB документы
repository/jpa/     — Spring Data JPA репозитории
repository/mongo/   — Spring Data MongoDB репозитории
config/             — конфигурационные классы
```

### Database Strategy

- **PostgreSQL** (JPA): `Product`, `ProductAvailability` — реляционные данные
- **MongoDB**: `Customer`, `Order` — документы
- **Liquibase**: миграции в `src/main/resources/db/changelog/`

### Code Generation

- **gRPC**: protobuf-maven-plugin генерирует код из `.proto` файлов
- **SOAP**: jaxb2-maven-plugin генерирует код из `.xsd` файлов в `ppr.ru.integrationservice.soap.generated`
- **MapStruct**: маперы в пакете `dto/` (`ProductMapper`, `CustomerMapper`, `OrderMapper`)

## Configuration

- Main config: `src/main/resources/application.yaml`
- PostgreSQL: `localhost:15436`
- MongoDB: `localhost:27017`
- gRPC: порт `9090`
