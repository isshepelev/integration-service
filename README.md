# Integration Service

Микросервис интеграции на базе Spring Boot 4.0.1, предоставляющий единый API для работы с продуктами, клиентами и заказами через различные протоколы.

## Технологии

- **Java 17** + **Spring Boot 4.0.1**
- **PostgreSQL** (Spring Data JPA) — реляционные данные (продукты)
- **MongoDB** (Spring Data MongoDB) — документы (клиенты, заказы)
- **Liquibase** — миграции БД

## API

| Протокол | Endpoint | Описание |
|----------|----------|----------|
| REST | `/api/products`, `/api/customers`, `/api/orders` | CRUD операции |
| GraphQL | `/graphql` | Запросы продуктов |
| gRPC | порт `9090` | Сервис продуктов |
| SOAP | `/ws` | Веб-сервис продуктов |

## Документация

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **GraphiQL**: http://localhost:8080/graphiql (интерактивный интерфейс)

## Запуск

### 1. Запуск контейнеров

Перед запуском приложения необходимо поднять контейнеры с базами данных:

```bash
  docker-compose up -d
```

### 2. Запуск приложения

После успешного запуска контейнеров можно запустить само приложение:

```bash
# Сборка
./mvnw clean package

# Запуск
./mvnw spring-boot:run

# Тесты
./mvnw test
```

## Требования

- Java 17+
- PostgreSQL (localhost:5432)
- MongoDB (localhost:27017)
