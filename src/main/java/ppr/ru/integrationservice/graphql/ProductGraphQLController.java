package ppr.ru.integrationservice.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ppr.ru.integrationservice.dto.CreateProductRequest;
import ppr.ru.integrationservice.dto.ProductDTO;
import ppr.ru.integrationservice.dto.UpdateProductRequest;
import ppr.ru.integrationservice.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductGraphQLController {

    private final ProductService productService;

    // ==================== Query mappings ====================

    @QueryMapping
    public List<ProductDTO> products() {
        return productService.getAllProducts();
    }

    @QueryMapping
    public List<ProductDTO> productsWithAvailability() {
        return productService.getAllProductsWithAvailability();
    }

    @QueryMapping
    public ProductDTO product(@Argument Long id) {
        return productService.getProductByIdWithAvailability(id).orElse(null);
    }

    @QueryMapping
    public ProductDTO productBySku(@Argument String sku) {
        return productService.getProductBySkuWithAvailability(sku).orElse(null);
    }

    @QueryMapping
    public List<ProductDTO> productsByCategory(@Argument String category) {
        return productService.getProductsByCategoryWithAvailability(category);
    }

    // ==================== Mutation mappings ====================

    @MutationMapping
    public ProductDTO createProduct(@Argument CreateProductInput input) {
        CreateProductRequest request = CreateProductRequest.builder()
                .name(input.name())
                .description(input.description())
                .price(input.price())
                .sku(input.sku())
                .category(input.category())
                .build();
        return productService.createProduct(request);
    }

    @MutationMapping
    public ProductDTO updateProduct(@Argument Long id, @Argument UpdateProductInput input) {
        UpdateProductRequest request = UpdateProductRequest.builder()
                .name(input.name())
                .description(input.description())
                .price(input.price())
                .sku(input.sku())
                .category(input.category())
                .build();
        return productService.updateProduct(id, request).orElse(null);
    }

    @MutationMapping
    public boolean deleteProduct(@Argument Long id) {
        return productService.deleteProduct(id);
    }

    // ==================== GraphQL Input Records ====================

    public record CreateProductInput(
            String name,
            String description,
            BigDecimal price,
            String sku,
            String category
    ) {}

    public record UpdateProductInput(
            String name,
            String description,
            BigDecimal price,
            String sku,
            String category
    ) {}
}
