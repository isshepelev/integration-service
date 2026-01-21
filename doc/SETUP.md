# Установка и настройка

## Содержание

1. [Системные требования](#системные-требования)
2. [Установка зависимостей](#установка-зависимостей)
3. [Настройка баз данных](#настройка-баз-данных)
4. [Конфигурация приложения](#конфигурация-приложения)
5. [Сборка и запуск](#сборка-и-запуск)
6. [Проверка работоспособности](#проверка-работоспособности)
7. [Решение проблем](#решение-проблем)

---

## Системные требования

| Компонент | Минимальная версия |
|-----------|-------------------|
| Java | 17 |
| Maven | 3.8+ |
| Docker | 20.10+ |
| Docker Compose | 2.0+ |

### Проверка версий

```bash
java -version
mvn -version
docker --version
docker compose version
```

---

## Установка зависимостей

### macOS

```bash
# Установка Homebrew (если не установлен)
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# Установка Java 17
brew install openjdk@17

# Установка Maven
brew install maven

# Установка Docker Desktop
brew install --cask docker
```

### Ubuntu/Debian

```bash
# Установка Java 17
sudo apt update
sudo apt install openjdk-17-jdk

# Установка Maven
sudo apt install maven

# Установка Docker
sudo apt install docker.io docker-compose-v2
sudo usermod -aG docker $USER
```

### Windows

1. Скачайте и установите [JDK 17](https://adoptium.net/)
2. Скачайте и установите [Maven](https://maven.apache.org/download.cgi)
3. Скачайте и установите [Docker Desktop](https://www.docker.com/products/docker-desktop/)

---

## Настройка баз данных

### Вариант 1: Docker (рекомендуется)

**Запуск PostgreSQL:**

```bash
docker run -d \
  --name postgres \
  -p 5432:5432 \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=postgres \
  -v postgres_data:/var/lib/postgresql/data \
  postgres:15
```

**Запуск MongoDB:**

```bash
docker run -d \
  --name mongodb \
  -p 27017:27017 \
  -v mongodb_data:/data/db \
  mongo:7
```

**Проверка запуска:**

```bash
docker ps
```

### Вариант 2: Docker Compose

Создайте файл `docker-compose.yml`:

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:15
    container_name: postgres
    ports:
      - "5432:5432"
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: postgres
    volumes:
      - postgres_data:/var/lib/postgresql/data

  mongodb:
    image: mongo:7
    container_name: mongodb
    ports:
      - "27017:27017"
    volumes:
      - mongodb_data:/data/db

volumes:
  postgres_data:
  mongodb_data:
```

**Запуск:**

```bash
docker compose up -d
```

### Вариант 3: Локальная установка

При локальной установке баз данных убедитесь, что:

- PostgreSQL запущен на порту 5432
- MongoDB запущен на порту 27017
- Созданы необходимые пользователи и базы данных

---

## Конфигурация приложения

Конфигурация находится в файле `src/main/resources/application.yaml`.

### Основные параметры

```yaml
spring:
  # PostgreSQL
  datasource:
    url: jdbc:postgresql://localhost:5432/postgres
    username: postgres
    password: postgres

  # MongoDB
  data:
    mongodb:
      uri: mongodb://localhost:27017/mydatabase

  # GraphQL
  graphql:
    graphiql:
      enabled: true  # Включить GraphiQL IDE

# gRPC
grpc:
  server:
    port: 9090
```

### Профили окружения

Для разных окружений создайте файлы:

- `application-dev.yaml` — для разработки
- `application-prod.yaml` — для продакшена

**Запуск с профилем:**

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### Переменные окружения

Параметры можно переопределить через переменные окружения:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://db-server:5432/mydb
export SPRING_DATASOURCE_USERNAME=myuser
export SPRING_DATASOURCE_PASSWORD=mypassword
export SPRING_DATA_MONGODB_URI=mongodb://mongo-server:27017/mydb
```

---

## Сборка и запуск

### Сборка проекта

```bash
# Полная сборка с тестами
./mvnw clean package

# Сборка без тестов
./mvnw clean package -DskipTests

# Только компиляция
./mvnw compile
```

### Запуск приложения

**Через Maven:**

```bash
./mvnw spring-boot:run
```

**Через JAR файл:**

```bash
java -jar target/integration-service-0.0.1-SNAPSHOT.jar
```

**С указанием профиля:**

```bash
java -jar target/integration-service-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

### Сборка Docker образа

```bash
./mvnw spring-boot:build-image
```

---

## Проверка работоспособности

### 1. REST API

```bash
curl http://localhost:8080/api/products
```

Ожидаемый результат: JSON массив с продуктами.

### 2. GraphQL

Откройте в браузере: http://localhost:8080/graphiql

Выполните запрос:

```graphql
{
  products {
    id
    name
    price
  }
}
```

### 3. SOAP

```bash
curl http://localhost:8080/ws/products.wsdl
```

Ожидаемый результат: XML с WSDL описанием.

### 4. gRPC

```bash
# Установка grpcurl
brew install grpcurl  # macOS

# Проверка сервисов
grpcurl -plaintext localhost:9090 list

# Вызов метода
grpcurl -plaintext -d '{}' localhost:9090 product.ProductService/GetProducts
```

---

## Решение проблем

### Ошибка подключения к PostgreSQL

**Симптом:** `Connection refused` или `FATAL: password authentication failed`

**Решение:**
1. Проверьте, что PostgreSQL запущен: `docker ps`
2. Проверьте параметры подключения в `application.yaml`
3. Проверьте доступность порта: `nc -zv localhost 5432`

### Ошибка подключения к MongoDB

**Симптом:** `MongoTimeoutException`

**Решение:**
1. Проверьте, что MongoDB запущен: `docker ps`
2. Проверьте URI в `application.yaml`
3. Проверьте доступность порта: `nc -zv localhost 27017`

### Порт уже занят

**Симптом:** `Address already in use`

**Решение:**

```bash
# Найти процесс на порту 8080
lsof -i :8080

# Завершить процесс
kill -9 <PID>
```

Или измените порт в `application.yaml`:

```yaml
server:
  port: 8081
```

### Ошибки компиляции gRPC

**Симптом:** `cannot find symbol: class Generated`

**Решение:** Убедитесь, что в `pom.xml` добавлена зависимость:

```xml
<dependency>
    <groupId>javax.annotation</groupId>
    <artifactId>javax.annotation-api</artifactId>
    <version>1.3.2</version>
</dependency>
```

### Liquibase не запускается

**Симптом:** `LiquibaseException: Migration failed`

**Решение:**
1. Проверьте структуру YAML файлов миграций
2. Удалите таблицы `databasechangelog` и `databasechangeloglock` для полного перезапуска
3. Проверьте права доступа к базе данных

```sql
DROP TABLE IF EXISTS databasechangeloglock;
DROP TABLE IF EXISTS databasechangelog;
```

---

## Логирование

### Настройка уровня логов

В `application.yaml`:

```yaml
logging:
  level:
    root: INFO
    ppr.ru.integrationservice: DEBUG
    org.springframework.web: DEBUG
    org.hibernate.SQL: DEBUG
```

### Просмотр логов в Docker

```bash
docker logs -f postgres
docker logs -f mongodb
```

---

## Контакты поддержки

При возникновении проблем обращайтесь в отдел разработки.
