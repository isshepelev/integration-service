# Integration Service

## Обзор

Integration Service — это микросервис на базе Spring Boot 4.0.1, предоставляющий доступ к данным магазина через четыре протокола:

- **REST API** — для веб и мобильных приложений
- **GraphQL** — для гибких запросов с клиента
- **gRPC** — для высокопроизводительного межсервисного взаимодействия
- **SOAP** — для интеграции с legacy-системами

## Технологический стек

| Компонент | Технология |
|-----------|------------|
| Язык | Java 17 |
| Фреймворк | Spring Boot 4.0.1 |
| Реляционная БД | PostgreSQL + Spring Data JPA |
| Документная БД | MongoDB + Spring Data MongoDB |
| Миграции | Liquibase |
| Маппинг DTO | MapStruct 1.5.5 |
| gRPC | grpc-java 1.58.0 |
| API Документация | SpringDoc OpenAPI 2.3.0 (Swagger UI) |
| Шаблонизатор | Thymeleaf |
| Сборка | Maven |

## Структура проекта

```
src/main/java/ppr/ru/integrationservice/
├── config/                 # Конфигурация (SOAP и др.)
├── controller/rest/        # REST контроллеры
├── dto/                    # DTO и мапперы
├── graphql/                # GraphQL контроллеры
├── grpc/                   # gRPC сервисы
├── model/
│   ├── jpa/               # JPA сущности (PostgreSQL)
│   └── mongo/             # MongoDB документы
├── repository/
│   ├── jpa/               # JPA репозитории
│   └── mongo/             # MongoDB репозитории
├── service/               # Бизнес-логика
│   └── impl/
└── soap/endpoint/         # SOAP эндпоинты

src/main/resources/
├── db/changelog/          # Liquibase миграции
├── graphql/               # GraphQL схема
└── xsd/                   # XSD схемы для SOAP

src/main/proto/            # Protobuf файлы для gRPC
```

## Быстрый старт

### Предварительные требования

- Java 17+
- Docker и Docker Compose (для БД)
- Maven 3.8+

### Запуск баз данных

```bash
docker run -d --name postgres -p 5432:5432 \
  -e POSTGRES_PASSWORD=postgres postgres:15

docker run -d --name mongodb -p 27017:27017 mongo:7
```

### Сборка и запуск

```bash
# Сборка
./mvnw clean package

# Запуск
./mvnw spring-boot:run
```

### Проверка работоспособности

После запуска откройте в браузере главную страницу:

```
http://localhost:8080/
```

На главной странице доступны кнопки для перехода ко всей документации API.

### Доступные эндпоинты

| Компонент | URL | Описание |
|-----------|-----|----------|
| **Главная страница** | http://localhost:8080/ | Веб-интерфейс с навигацией |
| **Swagger UI** | http://localhost:8080/swagger-ui.html | Интерактивная документация REST API |
| **OpenAPI JSON** | http://localhost:8080/v3/api-docs | OpenAPI спецификация |
| REST API | http://localhost:8080/api/products | Список продуктов |
| GraphiQL | http://localhost:8080/graphiql | GraphQL IDE |
| WSDL | http://localhost:8080/ws/products.wsdl | SOAP описание |
| gRPC | localhost:9090 | gRPC сервер |

## Документация

- [API документация](API.md) — описание всех API эндпоинтов
- [GraphQL запросы](GRAPHQL_QUERIES.md) — примеры GraphQL запросов
- [Структура БД](DATABASE.md) — схема базы данных
- [Установка и настройка](SETUP.md) — подробные инструкции по развертыванию

## Лицензия

Проприетарное ПО. Все права защищены.
