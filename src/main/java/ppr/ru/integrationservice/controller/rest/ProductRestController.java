package ppr.ru.integrationservice.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ppr.ru.integrationservice.dto.ProductDTO;
import ppr.ru.integrationservice.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false, defaultValue = "false") boolean includeAvailability) {

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

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "false") boolean includeAvailability) {

        return (includeAvailability
                ? productService.getProductByIdWithAvailability(id)
                : productService.getProductById(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<ProductDTO> getProductBySku(
            @PathVariable String sku,
            @RequestParam(required = false, defaultValue = "false") boolean includeAvailability) {

        return (includeAvailability
                ? productService.getProductBySkuWithAvailability(sku)
                : productService.getProductBySku(sku))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
