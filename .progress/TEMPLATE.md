# TEMPLATE.md - Шаблоны для агентов

## Шаблон: gRPC Example Tab

```html
<!-- Tab N: Create Product -->
<div class="tab-panel" id="tabN">
    <div class="example-content">
        <div class="example-header">
            <h3>Create Product</h3>
            <span class="category-badge basic">POSTGRESQL</span>
        </div>
        <div class="code-panels">
            <div class="code-block request">
                <div class="code-label">
                    <span>GRPCURL COMMAND</span>
                    <button class="copy-btn" onclick="copyCode(this)">
                        <span class="copy-icon">&#x2398;</span> COPY
                    </button>
                </div>
                <pre><code>grpcurl -plaintext \
  -d '{
    "name": "Умная колонка Яндекс Станция Макс",
    "description": "Умная колонка с Алисой, LED-дисплей, 65 Вт",
    "price": 24990.00,
    "sku": "YA-STM-BLK",
    "category": "Электроника"
  }' \
  localhost:<span th:text="${grpcPort}">9090</span> \
  product.ProductService/CreateProduct</code></pre>
            </div>
            <div class="code-block response">
                <div class="code-label">
                    <span>RESPONSE</span>
                </div>
                <pre><code>{
  "product": {
    "id": "11",
    "name": "Умная колонка Яндекс Станция Макс",
    "description": "Умная колонка с Алисой, LED-дисплей, 65 Вт",
    "price": 24990,
    "sku": "YA-STM-BLK",
    "category": "Электроника"
  },
  "found": true
}</code></pre>
            </div>
        </div>
    </div>
</div>
```

---

## Шаблон: GraphQL Example Tab

```html
<!-- Tab N: Create Product -->
<div class="tab-panel" id="tabN">
    <div class="example-content">
        <div class="example-header">
            <h3>Create Product</h3>
            <span class="category-badge basic">POSTGRESQL</span>
        </div>
        <div class="code-panels">
            <div class="code-block request">
                <div class="code-label">
                    <span>REQUEST</span>
                    <button class="copy-btn" onclick="copyCode(this)">
                        <span class="copy-icon">&#x2398;</span> COPY
                    </button>
                </div>
                <pre><code>mutation CreateProduct($input: CreateProductInput!) {
  createProduct(input: $input) {
    id
    name
    description
    price
    sku
    category
  }
}

# Variables:
# {
#   "input": {
#     "name": "Умная колонка Яндекс Станция Макс",
#     "description": "Умная колонка с Алисой",
#     "price": 24990.00,
#     "sku": "YA-STM-BLK",
#     "category": "Электроника"
#   }
# }</code></pre>
            </div>
            <div class="code-block response">
                <div class="code-label">
                    <span>RESPONSE</span>
                </div>
                <pre><code>{
  "data": {
    "createProduct": {
      "id": "11",
      "name": "Умная колонка Яндекс Станция Макс",
      "description": "Умная колонка с Алисой",
      "price": 24990.0,
      "sku": "YA-STM-BLK",
      "category": "Электроника"
    }
  }
}</code></pre>
            </div>
        </div>
        <div class="example-actions">
            <a th:href="@{/graphql-playground}" class="action-btn try-btn">
                <span class="btn-icon">&#9654;</span> TRY IN PLAYGROUND
            </a>
        </div>
    </div>
</div>
```

---

## Шаблон: SOAP Example Tab

```html
<!-- Tab N: Create Product -->
<div class="tab-panel" id="tabN">
    <div class="example-content">
        <div class="example-header">
            <h3>Create Product</h3>
            <span class="category-badge basic">POSTGRESQL</span>
        </div>
        <div class="code-panels">
            <div class="code-block request">
                <div class="code-label">
                    <span>REQUEST</span>
                    <button class="copy-btn" onclick="copyCode(this)">
                        <span class="copy-icon">&#x2398;</span> COPY
                    </button>
                </div>
                <pre><code>&lt;soapenv:Envelope
    xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
    xmlns:prod="http://integrationservice.ru.ppr/soap/products"&gt;
   &lt;soapenv:Header/&gt;
   &lt;soapenv:Body&gt;
      &lt;prod:createProductRequest&gt;
         &lt;prod:name&gt;Умная колонка Яндекс Станция Макс&lt;/prod:name&gt;
         &lt;prod:description&gt;Умная колонка с Алисой&lt;/prod:description&gt;
         &lt;prod:price&gt;24990.00&lt;/prod:price&gt;
         &lt;prod:sku&gt;YA-STM-BLK&lt;/prod:sku&gt;
         &lt;prod:category&gt;Электроника&lt;/prod:category&gt;
      &lt;/prod:createProductRequest&gt;
   &lt;/soapenv:Body&gt;
&lt;/soapenv:Envelope&gt;</code></pre>
            </div>
            <div class="code-block response">
                <div class="code-label">
                    <span>RESPONSE</span>
                </div>
                <pre><code>&lt;SOAP-ENV:Envelope xmlns:SOAP-ENV="..."&gt;
   &lt;SOAP-ENV:Body&gt;
      &lt;ns2:createProductResponse xmlns:ns2="..."&gt;
         &lt;ns2:product&gt;
            &lt;ns2:id&gt;11&lt;/ns2:id&gt;
            &lt;ns2:name&gt;Умная колонка Яндекс Станция Макс&lt;/ns2:name&gt;
            &lt;ns2:description&gt;Умная колонка с Алисой&lt;/ns2:description&gt;
            &lt;ns2:price&gt;24990.00&lt;/ns2:price&gt;
            &lt;ns2:sku&gt;YA-STM-BLK&lt;/ns2:sku&gt;
            &lt;ns2:category&gt;Электроника&lt;/ns2:category&gt;
         &lt;/ns2:product&gt;
      &lt;/ns2:createProductResponse&gt;
   &lt;/SOAP-ENV:Body&gt;
&lt;/SOAP-ENV:Envelope&gt;</code></pre>
            </div>
        </div>
    </div>
</div>
```

---

## Тестовые данные для примеров

### Create Product
```json
{
  "name": "Умная колонка Яндекс Станция Макс",
  "description": "Умная колонка с Алисой, LED-дисплей, 65 Вт",
  "price": 24990.00,
  "sku": "YA-STM-BLK",
  "category": "Электроника"
}
```

### Update Product (ID=1)
```json
{
  "id": 1,
  "name": "Смартфон Samsung Galaxy S24 Ultra",
  "description": "Обновленный флагман с улучшенной камерой 200 МП",
  "price": 109990.00,
  "sku": "SAM-S24U-BLK",
  "category": "Электроника"
}
```

### Delete Product
```json
{
  "id": 11
}
```
