# HOW_TO_RUN.md - Инструкции запуска агентов

## Обзор

Данный документ описывает как запустить параллельные агенты для выполнения задачи.

---

## Фаза 1: Backend (3 параллельных агента)

### Terminal 1: gRPC Backend Agent

```bash
cd /Users/michaelsmirnov/Desktop/work/projects/integration-service
```

**Prompt:**
```
Прочитай .progress/SONNET_PLAN.md (модуль A).
Выполни задачи:
1. Обнови src/main/proto/product.proto - добавь CreateProduct, UpdateProduct, DeleteProduct RPC методы
2. Обнови src/main/java/ppr/ru/integrationservice/grpc/ProductGrpcService.java - реализуй методы
После завершения отметь [x] в .progress/PROGRESS.md для Agent 1
```

### Terminal 2: GraphQL Backend Agent

```bash
cd /Users/michaelsmirnov/Desktop/work/projects/integration-service
```

**Prompt:**
```
Прочитай .progress/SONNET_PLAN.md (модуль B).
Выполни задачи:
1. Обнови src/main/resources/graphql/schema.graphqls - добавь Product мутации
2. Обнови src/main/java/ppr/ru/integrationservice/graphql/ProductGraphQLController.java
После завершения отметь [x] в .progress/PROGRESS.md для Agent 2
```

### Terminal 3: SOAP Backend Agent

```bash
cd /Users/michaelsmirnov/Desktop/work/projects/integration-service
```

**Prompt:**
```
Прочитай .progress/SONNET_PLAN.md (модуль C).
Выполни задачи:
1. Обнови src/main/resources/xsd/products.xsd - добавь CRUD операции
2. Обнови src/main/java/ppr/ru/integrationservice/soap/endpoint/ProductSoapEndpoint.java
После завершения отметь [x] в .progress/PROGRESS.md для Agent 3
```

---

## Фаза 2: Frontend HTML (3 параллельных агента)

### Terminal 4: gRPC Examples Agent

**Prompt:**
```
Прочитай .progress/SONNET_PLAN.md (модуль D).
Обнови src/main/resources/templates/grpc-examples.html:
1. Добавь CreateProduct, UpdateProduct, DeleteProduct в SERVICE DEFINITION
2. Добавь 3 новых таба в секцию PRODUCTS
3. Обнови PROTO DEFINITIONS
4. Обнови header stats (METHODS: 24)
```

### Terminal 5: GraphQL Examples Agent

**Prompt:**
```
Прочитай .progress/SONNET_PLAN.md (модуль E).
Обнови src/main/resources/templates/graphql-examples.html:
1. Добавь Product мутации в SCHEMA OVERVIEW
2. Добавь 3 новых таба в секцию PRODUCTS
3. Обнови header stats
```

### Terminal 6: SOAP Examples Agent

**Prompt:**
```
Прочитай .progress/SONNET_PLAN.md (модуль F).
Обнови src/main/resources/templates/soap-examples.html:
1. Обнови WSDL SCHEMA OVERVIEW для Products
2. Добавь 3 новых таба в секцию PRODUCTS
3. Обнови header stats
```

---

## Фаза 3: Верификация

```bash
# Компиляция (проверяет proto и xsd генерацию)
./mvnw compile

# Тесты
./mvnw test
```

---

## Важные примечания

1. **Конфликты:** Агенты работают с разными файлами, конфликты маловероятны
2. **Зависимости:** Фаза 2 зависит от Фазы 1 (генерация кода из proto/xsd)
3. **Прогресс:** Каждый агент отмечает завершение в PROGRESS.md
