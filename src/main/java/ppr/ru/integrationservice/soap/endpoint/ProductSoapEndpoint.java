package ppr.ru.integrationservice.soap.endpoint;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ppr.ru.integrationservice.dto.CreateProductRequest;
import ppr.ru.integrationservice.dto.ProductDTO;
import ppr.ru.integrationservice.dto.UpdateProductRequest;
import ppr.ru.integrationservice.service.ProductService;
import ppr.ru.integrationservice.soap.generated.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Endpoint
@RequiredArgsConstructor
public class ProductSoapEndpoint {

    private static final String NAMESPACE_URI = "http://integrationservice.ru.ppr/soap/products";

    private final ProductService productService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductsRequest")
    @ResponsePayload
    public GetProductsResponse getProducts(@RequestPayload GetProductsRequest request) {
        boolean includeAvailability = request.isIncludeAvailability() != null && request.isIncludeAvailability();
        String category = request.getCategory();

        List<ProductDTO> products;
        if (category != null && !category.isBlank()) {
            products = includeAvailability
                    ? productService.getProductsByCategoryWithAvailability(category)
                    : productService.getProductsByCategory(category);
        } else {
            products = includeAvailability
                    ? productService.getAllProductsWithAvailability()
                    : productService.getAllProducts();
        }

        GetProductsResponse response = new GetProductsResponse();
        products.forEach(dto -> response.getProduct().add(toSoapProduct(dto)));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
    @ResponsePayload
    public GetProductResponse getProductById(@RequestPayload GetProductByIdRequest request) {
        boolean includeAvailability = request.isIncludeAvailability() != null && request.isIncludeAvailability();

        Optional<ProductDTO> product = includeAvailability
                ? productService.getProductByIdWithAvailability(request.getId())
                : productService.getProductById(request.getId());

        GetProductResponse response = new GetProductResponse();
        product.ifPresent(dto -> response.setProduct(toSoapProduct(dto)));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductBySkuRequest")
    @ResponsePayload
    public GetProductResponse getProductBySku(@RequestPayload GetProductBySkuRequest request) {
        boolean includeAvailability = request.isIncludeAvailability() != null && request.isIncludeAvailability();

        Optional<ProductDTO> product = includeAvailability
                ? productService.getProductBySkuWithAvailability(request.getSku())
                : productService.getProductBySku(request.getSku());

        GetProductResponse response = new GetProductResponse();
        product.ifPresent(dto -> response.setProduct(toSoapProduct(dto)));
        return response;
    }

    // ==================== Mutation Operations ====================

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createProductRequest")
    @ResponsePayload
    public CreateProductResponse createProduct(@RequestPayload ppr.ru.integrationservice.soap.generated.CreateProductRequest request) {
        CreateProductRequest serviceRequest = CreateProductRequest.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .sku(request.getSku())
                .category(request.getCategory())
                .build();

        ProductDTO created = productService.createProduct(serviceRequest);

        CreateProductResponse response = new CreateProductResponse();
        response.setProduct(toSoapProduct(created));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateProductRequest")
    @ResponsePayload
    public UpdateProductResponse updateProduct(@RequestPayload ppr.ru.integrationservice.soap.generated.UpdateProductRequest request) {
        UpdateProductRequest serviceRequest = UpdateProductRequest.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .sku(request.getSku())
                .category(request.getCategory())
                .build();

        Optional<ProductDTO> updated = productService.partialUpdateProduct(request.getId(), serviceRequest);

        UpdateProductResponse response = new UpdateProductResponse();
        updated.ifPresent(dto -> response.setProduct(toSoapProduct(dto)));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteProductRequest")
    @ResponsePayload
    public DeleteProductResponse deleteProduct(@RequestPayload DeleteProductRequest request) {
        boolean success = productService.deleteProduct(request.getId());

        DeleteProductResponse response = new DeleteProductResponse();
        response.setSuccess(success);
        return response;
    }

    // ==================== Helper Methods ====================

    private Product toSoapProduct(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setSku(dto.getSku());
        product.setCategory(dto.getCategory());

        if (dto.getAvailabilities() != null) {
            dto.getAvailabilities().forEach(avail -> {
                ProductAvailability soapAvail = new ProductAvailability();
                soapAvail.setId(avail.getId());
                soapAvail.setQuantity(avail.getQuantity());
                soapAvail.setWarehouse(avail.getWarehouse());
                if (avail.getLastUpdated() != null) {
                    soapAvail.setLastUpdated(avail.getLastUpdated().toString());
                }
                product.getAvailability().add(soapAvail);
            });
        }

        return product;
    }
}
