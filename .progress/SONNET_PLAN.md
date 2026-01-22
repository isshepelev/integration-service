# SONNET_PLAN.md - Детальный план для агентов

## Обзор проекта

**Задача:** Добавить CRUD операции (Create, Update, Delete) для Products в три протокола API

**Язык:** Русский (для описаний), English (для кода)

---

## МОДУЛЬ A: gRPC Backend

### A1. product.proto - Добавить RPC методы

**Что включить:**
1. Добавить 3 новых RPC метода в ProductService
2. Добавить 4 новых message типа

```protobuf
// Добавить в service ProductService:
rpc CreateProduct(CreateProductRequest) returns (ProductResponse);
rpc UpdateProduct(UpdateProductRequest) returns (ProductResponse);
rpc DeleteProduct(DeleteProductRequest) returns (DeleteProductResponse);

// Добавить messages:
message CreateProductRequest {
    string name = 1;
    string description = 2;
    double price = 3;
    string sku = 4;
    string category = 5;
}

message UpdateProductRequest {
    int64 id = 1;
    string name = 2;
    string description = 3;
    double price = 4;
    string sku = 5;
    string category = 6;
}

message DeleteProductRequest {
    int64 id = 1;
}

message DeleteProductResponse {
    bool success = 1;
}
```

### A2. ProductGrpcService.java - Реализация методов

**Что включить:**
1. Метод createProduct() - вызывает productService.createProduct()
2. Метод updateProduct() - вызывает productService.updateProduct()
3. Метод deleteProduct() - вызывает productService.deleteProduct()

---

## МОДУЛЬ B: GraphQL Backend

### B1. schema.graphqls - Добавить мутации

**Что включить:**
1. Добавить 3 мутации в type Mutation
2. Добавить 2 input типа

```graphql
# В type Mutation добавить:
createProduct(input: CreateProductInput!): Product!
updateProduct(id: ID!, input: UpdateProductInput!): Product
deleteProduct(id: ID!): Boolean!

# Добавить input типы:
input CreateProductInput {
    name: String!
    description: String
    price: Float!
    sku: String!
    category: String
}

input UpdateProductInput {
    name: String
    description: String
    price: Float
    sku: String
    category: String
}
```

### B2. ProductGraphQLController.java - Реализация

**Что включить:**
1. @MutationMapping createProduct()
2. @MutationMapping updateProduct()
3. @MutationMapping deleteProduct()

---

## МОДУЛЬ C: SOAP Backend

### C1. products.xsd - Добавить операции

**Что включить:**
6 новых элементов (request/response пары)

### C2. ProductSoapEndpoint.java - Реализация

**Что включить:**
1. @PayloadRoot createProduct()
2. @PayloadRoot updateProduct()
3. @PayloadRoot deleteProduct()

---

## МОДУЛЬ D: gRPC Examples HTML

### D1. grpc-examples.html

**Что включить:**
1. Обновить SERVICE DEFINITION - добавить 3 метода
2. Добавить 3 таба в секцию PRODUCTS:
   - Create Product (команда grpcurl + JSON body)
   - Update Product
   - Delete Product
3. Обновить PROTO DEFINITIONS секцию
4. Обновить header stats (METHODS: 21 → 24)

---

## МОДУЛЬ E: GraphQL Examples HTML

### E1. graphql-examples.html

**Что включить:**
1. Обновить SCHEMA OVERVIEW - Mutation card
2. Добавить 3 таба в секцию PRODUCTS
3. Обновить header stats

---

## МОДУЛЬ F: SOAP Examples HTML

### F1. soap-examples.html

**Что включить:**
1. Обновить WSDL SCHEMA OVERVIEW
2. Добавить 3 таба в секцию PRODUCTS
3. Обновить header stats

---

## Распределение агентов

| Инстанс | Модули | Кол-во задач |
|---------|--------|--------------|
| Agent 1 | A (gRPC Backend) | 2 файла |
| Agent 2 | B (GraphQL Backend) | 2 файла |
| Agent 3 | C (SOAP Backend) | 2 файла |
| Agent 4 | D (gRPC HTML) | 1 файл |
| Agent 5 | E (GraphQL HTML) | 1 файл |
| Agent 6 | F (SOAP HTML) | 1 файл |

---

## Тестовые данные

```json
// Для Create примера:
{
  "name": "Умная колонка Яндекс Станция Макс",
  "description": "Умная колонка с Алисой, LED-дисплей, 65 Вт",
  "price": 24990.00,
  "sku": "YA-STM-BLK",
  "category": "Электроника"
}

// Для Update примера (ID=1):
{
  "id": 1,
  "name": "Смартфон Samsung Galaxy S24 Ultra",
  "description": "Обновленное описание",
  "price": 109990.00,
  "sku": "SAM-S24U-BLK",
  "category": "Электроника"
}

// Для Delete примера:
{
  "id": 11
}
```
