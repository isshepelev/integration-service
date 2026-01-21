# API Документация

## Содержание

1. [REST API](#rest-api)
2. [GraphQL API](#graphql-api)
3. [gRPC API](#grpc-api)
4. [SOAP API](#soap-api)

---

## REST API

Базовый URL: `http://localhost:8080/api`

### Получение списка продуктов

```
GET /api/products
```

**Параметры запроса:**

| Параметр | Тип | Обязательный | Описание |
|----------|-----|--------------|----------|
| category | string | Нет | Фильтр по категории |
| includeAvailability | boolean | Нет | Включить информацию о наличии (по умолчанию: false) |

**Примеры запросов:**

```bash
# Все продукты
curl http://localhost:8080/api/products

# Продукты категории "Электроника"
curl "http://localhost:8080/api/products?category=Электроника"

# С информацией о наличии
curl "http://localhost:8080/api/products?includeAvailability=true"
```

**Пример ответа:**

```json
[
  {
    "id": 1,
    "name": "Смартфон Samsung Galaxy S24",
    "description": "Флагманский смартфон с AI функциями",
    "price": 89990.00,
    "sku": "SAM-S24-BLK",
    "category": "Электроника",
    "availabilities": [
      {
        "id": 1,
        "quantity": 50,
        "warehouse": "Москва-Центр",
        "lastUpdated": "2024-01-15T10:30:00"
      }
    ]
  }
]
```

### Получение продукта по ID

```
GET /api/products/{id}
```

**Параметры пути:**

| Параметр | Тип | Описание |
|----------|-----|----------|
| id | long | Идентификатор продукта |

**Пример:**

```bash
curl http://localhost:8080/api/products/1
```

### Получение продукта по SKU

```
GET /api/products/sku/{sku}
```

**Пример:**

```bash
curl http://localhost:8080/api/products/sku/SAM-S24-BLK
```

---

## GraphQL API

URL: `http://localhost:8080/graphql`
GraphiQL IDE: `http://localhost:8080/graphiql`

### Схема

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

### Примеры запросов

**Получение всех продуктов:**

```graphql
query {
  products {
    id
    name
    price
    sku
  }
}
```

**Получение продукта с наличием:**

```graphql
query {
  product(id: 1) {
    id
    name
    price
    availabilities {
      warehouse
      quantity
    }
  }
}
```

**Фильтрация по категории:**

```graphql
query {
  productsByCategory(category: "Электроника") {
    id
    name
    price
  }
}
```

**cURL пример:**

```bash
curl -X POST http://localhost:8080/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ products { id name price } }"}'
```

---

## gRPC API

Хост: `localhost:9090`

### Сервис ProductService

```protobuf
service ProductService {
    rpc GetProducts(GetProductsRequest) returns (GetProductsResponse);
    rpc GetProductById(GetProductByIdRequest) returns (ProductResponse);
    rpc GetProductBySku(GetProductBySkuRequest) returns (ProductResponse);
    rpc GetProductsByCategory(GetProductsByCategoryRequest) returns (GetProductsResponse);
}
```

### Сообщения

```protobuf
message GetProductsRequest {
    bool include_availability = 1;
}

message GetProductByIdRequest {
    int64 id = 1;
    bool include_availability = 2;
}

message GetProductBySkuRequest {
    string sku = 1;
    bool include_availability = 2;
}

message GetProductsByCategoryRequest {
    string category = 1;
    bool include_availability = 2;
}

message Product {
    int64 id = 1;
    string name = 2;
    string description = 3;
    double price = 4;
    string sku = 5;
    string category = 6;
    repeated Availability availabilities = 7;
}

message Availability {
    int64 id = 1;
    int32 quantity = 2;
    string warehouse = 3;
    string last_updated = 4;
}
```

### Примеры использования

**С помощью grpcurl:**

```bash
# Установка grpcurl
brew install grpcurl

# Получение списка сервисов
grpcurl -plaintext localhost:9090 list

# Получение всех продуктов
grpcurl -plaintext -d '{"include_availability": true}' \
  localhost:9090 product.ProductService/GetProducts

# Получение продукта по ID
grpcurl -plaintext -d '{"id": 1, "include_availability": true}' \
  localhost:9090 product.ProductService/GetProductById
```

---

## SOAP API

WSDL: `http://localhost:8080/ws/products.wsdl`
Эндпоинт: `http://localhost:8080/ws`

### Пространство имен

```
http://integrationservice.ru.ppr/soap/products
```

### Операции

#### getProductsRequest

Получение списка продуктов с опциональной фильтрацией.

**Запрос:**

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:prod="http://integrationservice.ru.ppr/soap/products">
   <soapenv:Header/>
   <soapenv:Body>
      <prod:getProductsRequest>
         <prod:includeAvailability>true</prod:includeAvailability>
         <prod:category>Электроника</prod:category>
      </prod:getProductsRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

**Ответ:**

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
   <soapenv:Body>
      <ns2:getProductsResponse xmlns:ns2="http://integrationservice.ru.ppr/soap/products">
         <ns2:product>
            <ns2:id>1</ns2:id>
            <ns2:name>Смартфон Samsung Galaxy S24</ns2:name>
            <ns2:price>89990.00</ns2:price>
            <ns2:sku>SAM-S24-BLK</ns2:sku>
            <ns2:category>Электроника</ns2:category>
            <ns2:availability>
               <ns2:id>1</ns2:id>
               <ns2:quantity>50</ns2:quantity>
               <ns2:warehouse>Москва-Центр</ns2:warehouse>
            </ns2:availability>
         </ns2:product>
      </ns2:getProductsResponse>
   </soapenv:Body>
</soapenv:Envelope>
```

#### getProductByIdRequest

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:prod="http://integrationservice.ru.ppr/soap/products">
   <soapenv:Header/>
   <soapenv:Body>
      <prod:getProductByIdRequest>
         <prod:id>1</prod:id>
         <prod:includeAvailability>true</prod:includeAvailability>
      </prod:getProductByIdRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

#### getProductBySkuRequest

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:prod="http://integrationservice.ru.ppr/soap/products">
   <soapenv:Header/>
   <soapenv:Body>
      <prod:getProductBySkuRequest>
         <prod:sku>SAM-S24-BLK</prod:sku>
         <prod:includeAvailability>false</prod:includeAvailability>
      </prod:getProductBySkuRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

### Тестирование с cURL

```bash
curl -X POST http://localhost:8080/ws \
  -H "Content-Type: text/xml" \
  -d '<?xml version="1.0"?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:prod="http://integrationservice.ru.ppr/soap/products">
   <soapenv:Header/>
   <soapenv:Body>
      <prod:getProductsRequest>
         <prod:includeAvailability>true</prod:includeAvailability>
      </prod:getProductsRequest>
   </soapenv:Body>
</soapenv:Envelope>'
```

---

## Коды ответов

| Код | Описание |
|-----|----------|
| 200 | Успешный запрос |
| 404 | Ресурс не найден |
| 500 | Внутренняя ошибка сервера |
