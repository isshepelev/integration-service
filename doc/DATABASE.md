# Структура базы данных

## Обзор

Сервис использует две базы данных:

1. **PostgreSQL** — для реляционных данных (продукты, наличие)
2. **MongoDB** — для документов (клиенты, заказы)

---

## PostgreSQL

### Схема базы данных

```
┌─────────────────────────────────┐
│            products             │
├─────────────────────────────────┤
│ id          BIGINT PK           │
│ name        VARCHAR(255) NOT NULL│
│ description TEXT                │
│ price       DECIMAL(19,2) NOT NULL│
│ sku         VARCHAR(100) UNIQUE │
│ category    VARCHAR(100)        │
└─────────────────────────────────┘
              │
              │ 1:N
              ▼
┌─────────────────────────────────┐
│      product_availability       │
├─────────────────────────────────┤
│ id          BIGINT PK           │
│ product_id  BIGINT FK NOT NULL  │
│ quantity    INT NOT NULL        │
│ warehouse   VARCHAR(100) NOT NULL│
│ last_updated TIMESTAMP          │
└─────────────────────────────────┘
```

### Таблица products

Хранит информацию о продуктах магазина.

| Поле | Тип | Ограничения | Описание |
|------|-----|-------------|----------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Уникальный идентификатор |
| name | VARCHAR(255) | NOT NULL | Название продукта |
| description | TEXT | — | Описание продукта |
| price | DECIMAL(19,2) | NOT NULL | Цена в рублях |
| sku | VARCHAR(100) | NOT NULL, UNIQUE | Артикул товара |
| category | VARCHAR(100) | — | Категория товара |

**Индексы:**
- `idx_products_sku` — по полю sku
- `idx_products_category` — по полю category

### Таблица product_availability

Хранит информацию о наличии товаров на складах.

| Поле | Тип | Ограничения | Описание |
|------|-----|-------------|----------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Уникальный идентификатор |
| product_id | BIGINT | FOREIGN KEY, NOT NULL | Ссылка на продукт |
| quantity | INT | NOT NULL | Количество на складе |
| warehouse | VARCHAR(100) | NOT NULL | Название склада |
| last_updated | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Дата последнего обновления |

**Индексы:**
- `idx_product_availability_product_id` — по полю product_id

**Внешние ключи:**
- `fk_product_availability_product` — product_id → products.id

---

## MongoDB

### Коллекция customers

Хранит информацию о клиентах.

```json
{
  "_id": "ObjectId",
  "name": "String",
  "email": "String (unique index)",
  "phone": "String",
  "address": {
    "street": "String",
    "city": "String",
    "state": "String",
    "zipCode": "String",
    "country": "String"
  }
}
```

**Пример документа:**

```json
{
  "_id": "507f1f77bcf86cd799439011",
  "name": "Иванов Иван Иванович",
  "email": "ivanov@example.com",
  "phone": "+7 (999) 123-45-67",
  "address": {
    "street": "ул. Пушкина, д. 10, кв. 5",
    "city": "Москва",
    "state": "Московская область",
    "zipCode": "101000",
    "country": "Россия"
  }
}
```

**Индексы:**
- `email_1` — уникальный индекс по полю email

### Коллекция orders

Хранит информацию о заказах.

```json
{
  "_id": "ObjectId",
  "customerId": "String",
  "items": [
    {
      "productId": "Long",
      "productName": "String",
      "quantity": "Integer",
      "unitPrice": "Decimal",
      "subtotal": "Decimal"
    }
  ],
  "total": "Decimal",
  "status": "Enum (PENDING, CONFIRMED, PROCESSING, SHIPPED, DELIVERED, CANCELLED)",
  "createdAt": "DateTime",
  "updatedAt": "DateTime"
}
```

**Пример документа:**

```json
{
  "_id": "507f1f77bcf86cd799439012",
  "customerId": "507f1f77bcf86cd799439011",
  "items": [
    {
      "productId": 1,
      "productName": "Смартфон Samsung Galaxy S24",
      "quantity": 1,
      "unitPrice": 89990.00,
      "subtotal": 89990.00
    },
    {
      "productId": 3,
      "productName": "Чехол для Samsung Galaxy S24",
      "quantity": 2,
      "unitPrice": 1990.00,
      "subtotal": 3980.00
    }
  ],
  "total": 93970.00,
  "status": "CONFIRMED",
  "createdAt": "2024-01-15T14:30:00Z",
  "updatedAt": "2024-01-15T14:35:00Z"
}
```

**Статусы заказа:**

| Статус | Описание |
|--------|----------|
| PENDING | Ожидает подтверждения |
| CONFIRMED | Подтвержден |
| PROCESSING | В обработке |
| SHIPPED | Отправлен |
| DELIVERED | Доставлен |
| CANCELLED | Отменен |

---

## Liquibase миграции

Миграции расположены в директории `src/main/resources/db/changelog/`.

### Структура

```
db/changelog/
├── db.changelog-master.yaml    # Главный файл миграций
└── changes/
    ├── 001-create-product-tables.yaml  # Создание таблиц
    └── 002-insert-test-data.yaml       # Тестовые данные
```

### Выполнение миграций

Миграции выполняются автоматически при запуске приложения.

Для ручного запуска:

```bash
./mvnw liquibase:update
```

### Откат миграций

```bash
./mvnw liquibase:rollback -Dliquibase.rollbackCount=1
```

---

## Подключение к базам данных

### PostgreSQL

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/postgres
    username: postgres
    password: postgres
```

### MongoDB

```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/mydatabase
```

---

## Тестовые данные

При запуске приложения автоматически загружаются тестовые данные:

### PostgreSQL (products)

| ID | Название | Цена | SKU | Категория |
|----|----------|------|-----|-----------|
| 1 | Смартфон Samsung Galaxy S24 | 89 990 | SAM-S24-BLK | Электроника |
| 2 | Ноутбук Apple MacBook Pro 14 | 199 990 | APP-MBP14-SLV | Электроника |
| 3 | Наушники Sony WH-1000XM5 | 34 990 | SONY-WH1000-BLK | Электроника |
| 4 | Кофемашина DeLonghi Magnifica | 54 990 | DLG-MAG-BLK | Бытовая техника |
| 5 | Пылесос Dyson V15 | 69 990 | DYS-V15-GLD | Бытовая техника |

### MongoDB (customers)

- 3 тестовых клиента с адресами
- 5 тестовых заказов в разных статусах
