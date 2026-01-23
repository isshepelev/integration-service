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

## Документация и веб-интерфейс

Все страницы веб-интерфейса локализованы на русский язык.

| Страница | URL | Описание |
|----------|-----|----------|
| **Главная** | http://localhost:8080/ | Портал с навигацией по всем API |
| **Swagger UI** | http://localhost:8080/swagger-ui.html | REST API документация |
| **GraphiQL** | http://localhost:8080/graphiql | GraphQL IDE |
| **GraphQL примеры** | http://localhost:8080/graphql-examples | Примеры GraphQL запросов |
| **GraphQL Playground** | http://localhost:8080/graphql-playground | Интерактивный редактор GraphQL |
| **gRPC примеры** | http://localhost:8080/grpc-examples | Примеры gRPC вызовов |
| **SOAP примеры** | http://localhost:8080/soap-examples | Примеры SOAP запросов |
| **WSDL** | http://localhost:8080/ws/integration.wsdl | WSDL описание сервисов |

## Запуск

### 1. Запуск контейнеров

Для запуска приложения необходимо поднять контейнеры с приложением и базами данных:

```bash
  docker-compose up -d
```
В случае новой версии приложения необходимо использовать следующую команду:

```bash
  docker compose build --no-cache && docker compose up -d
```

## Требования

- Java 17+
- PostgreSQL (localhost:15436)
- MongoDB (localhost:27017)
