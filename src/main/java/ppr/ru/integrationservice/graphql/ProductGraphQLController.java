package ppr.ru.integrationservice.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ppr.ru.integrationservice.dto.ProductDTO;
import ppr.ru.integrationservice.service.ProductService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductGraphQLController {

    private final ProductService productService;

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
}
