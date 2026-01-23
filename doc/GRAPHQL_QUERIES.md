# GraphQL Запросы

## Содержание

1. [Начало работы](#начало-работы)
2. [Базовые запросы](#базовые-запросы)
3. [Запросы с параметрами](#запросы-с-параметрами)
4. [Оптимизированные запросы](#оптимизированные-запросы)
5. [Комбинированные запросы](#комбинированные-запросы)
6. [Примеры с cURL](#примеры-с-curl)

---

## Начало работы

### Веб-интерфейс (на русском языке)

| URL | Описание |
|-----|----------|
| http://localhost:8080/graphql-examples | Страница с примерами GraphQL запросов |
| http://localhost:8080/graphql-playground | Интерактивный редактор запросов |
| http://localhost:8080/graphiql | Стандартный GraphiQL IDE |

### Endpoint

```
POST http://localhost:8080/graphql
Content-Type: application/json
```

---

## Базовые запросы

### Получить все продукты

```graphql
query GetAllProducts {
  products {
    id
    name
    price
    sku
    category
  }
}
```

**Ответ:**
```json
{
  "data": {
    "products": [
      {
        "id": "1",
        "name": "Смартфон Samsung Galaxy S24",
        "price": 89990.0,
        "sku": "SAM-S24-BLK",
        "category": "Электроника"
      },
      {
        "id": "2",
        "name": "Ноутбук Apple MacBook Pro 14",
        "price": 199990.0,
        "sku": "APP-MBP14-SLV",
        "category": "Электроника"
      }
    ]
  }
}
```

### Получить все продукты с полными данными

```graphql
query GetAllProductsFull {
  products {
    id
    name
    description
    price
    sku
    category
  }
}
```

### Получить продукты с информацией о наличии

```graphql
query GetAllProductsWithAvailability {
  productsWithAvailability {
    id
    name
    price
    sku
    category
    availabilities {
      id
      quantity
      warehouse
      lastUpdated
    }
  }
}
```

**Ответ:**
```json
{
  "data": {
    "productsWithAvailability": [
      {
        "id": "1",
        "name": "Смартфон Samsung Galaxy S24",
        "price": 89990.0,
        "sku": "SAM-S24-BLK",
        "category": "Электроника",
        "availabilities": [
          {
            "id": "1",
            "quantity": 50,
            "warehouse": "Москва-Центр",
            "lastUpdated": "2024-01-15T10:30:00"
          },
          {
            "id": "2",
            "quantity": 30,
            "warehouse": "Санкт-Петербург",
            "lastUpdated": "2024-01-15T11:00:00"
          }
        ]
      }
    ]
  }
}
```

---

## Запросы с параметрами

### Получить продукт по ID

**Запрос:**
```graphql
query GetProductById($id: ID!) {
  product(id: $id) {
    id
    name
    description
    price
    sku
    category
    availabilities {
      quantity
      warehouse
      lastUpdated
    }
  }
}
```

**Переменные:**
```json
{
  "id": "1"
}
```

### Получить продукт по SKU

**Запрос:**
```graphql
query GetProductBySku($sku: String!) {
  productBySku(sku: $sku) {
    id
    name
    description
    price
    sku
    category
    availabilities {
      quantity
      warehouse
    }
  }
}
```

**Переменные:**
```json
{
  "sku": "SAM-S24-BLK"
}
```

### Получить продукты по категории

**Запрос:**
```graphql
query GetProductsByCategory($category: String!) {
  productsByCategory(category: $category) {
    id
    name
    price
    sku
  }
}
```

**Переменные:**
```json
{
  "category": "Электроника"
}
```

**Доступные категории:**
- `Электроника`
- `Бытовая техника`
- `Игры и развлечения`
- `Транспорт`

---

## Оптимизированные запросы

### Только названия и цены

```graphql
query GetProductNamesAndPrices {
  products {
    name
    price
  }
}
```

### Сводка по наличию

```graphql
query GetProductsStockSummary {
  productsWithAvailability {
    name
    sku
    availabilities {
      warehouse
      quantity
    }
  }
}
```

### Только идентификаторы

```graphql
query GetProductIdentifiers {
  products {
    id
    sku
  }
}
```

---

## Комбинированные запросы

### Несколько категорий в одном запросе

```graphql
query GetMultipleCategories {
  electronics: productsByCategory(category: "Электроника") {
    id
    name
    price
  }
  homeAppliances: productsByCategory(category: "Бытовая техника") {
    id
    name
    price
  }
  gaming: productsByCategory(category: "Игры и развлечения") {
    id
    name
    price
  }
}
```

### Несколько продуктов по ID

```graphql
query GetSpecificProducts {
  samsung: product(id: "1") {
    name
    price
    sku
  }
  macbook: product(id: "2") {
    name
    price
    sku
  }
  sony: product(id: "3") {
    name
    price
    sku
  }
}
```

### Поиск по SKU нескольких товаров

```graphql
query GetProductsBySku {
  phone: productBySku(sku: "SAM-S24-BLK") {
    name
    price
    availabilities {
      warehouse
      quantity
    }
  }
  laptop: productBySku(sku: "APP-MBP14-SLV") {
    name
    price
    availabilities {
      warehouse
      quantity
    }
  }
}
```

---

## Примеры с cURL

### Простой запрос

```bash
curl -X POST http://localhost:8080/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ products { id name price } }"}'
```

### Запрос с переменными

```bash
curl -X POST http://localhost:8080/graphql \
  -H "Content-Type: application/json" \
  -d '{
    "query": "query GetProduct($id: ID!) { product(id: $id) { id name price sku } }",
    "variables": { "id": "1" }
  }'
```

### Получить продукт по SKU

```bash
curl -X POST http://localhost:8080/graphql \
  -H "Content-Type: application/json" \
  -d '{
    "query": "query GetBySku($sku: String!) { productBySku(sku: $sku) { id name price } }",
    "variables": { "sku": "SAM-S24-BLK" }
  }'
```

### Получить продукты категории

```bash
curl -X POST http://localhost:8080/graphql \
  -H "Content-Type: application/json" \
  -d '{
    "query": "query GetByCategory($cat: String!) { productsByCategory(category: $cat) { name price } }",
    "variables": { "cat": "Электроника" }
  }'
```

### Продукты с наличием

```bash
curl -X POST http://localhost:8080/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ productsWithAvailability { name sku availabilities { warehouse quantity } } }"}'
```

---

## GraphQL Schema

```graphql
type Query {
    products: [Product!]!
    productsWithAvailability: [Product!]!
    product(id: ID!): Product
    productBySku(sku: String!): Product
    productsByCategory(category: String!): [Product!]!
}

type Product {
    id: ID!
    name: String!
    description: String
    price: Float!
    sku: String!
    category: String
    availabilities: [Availability!]
}

type Availability {
    id: ID!
    quantity: Int!
    warehouse: String!
    lastUpdated: String
}
```

---

## Тестовые данные

### Продукты

| ID | Название | SKU | Категория |
|----|----------|-----|-----------|
| 1 | Смартфон Samsung Galaxy S24 | SAM-S24-BLK | Электроника |
| 2 | Ноутбук Apple MacBook Pro 14 | APP-MBP14-SLV | Электроника |
| 3 | Наушники Sony WH-1000XM5 | SONY-WH1000-BLK | Электроника |
| 4 | Кофемашина DeLonghi Magnifica S | DLG-MAG-BLK | Бытовая техника |
| 5 | Пылесос Dyson V15 Detect | DYS-V15-GLD | Бытовая техника |
| 6 | Телевизор LG OLED 55" | LG-OLED55-BLK | Электроника |
| 7 | Игровая консоль Sony PlayStation 5 | SONY-PS5-WHT | Игры и развлечения |
| 8 | Умные часы Apple Watch Ultra 2 | APP-AWU2-TIT | Электроника |
| 9 | Холодильник Samsung Side-by-Side | SAM-SBS-SLVR | Бытовая техника |
| 10 | Электросамокат Xiaomi | XIA-ES4P-BLK | Транспорт |

### Склады

- Москва-Центр
- Санкт-Петербург
- Новосибирск
- Екатеринбург
- Казань
- Краснодар
- Москва-Склад
