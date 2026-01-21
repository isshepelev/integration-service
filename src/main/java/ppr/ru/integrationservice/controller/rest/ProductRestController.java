package ppr.ru.integrationservice.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ppr.ru.integrationservice.dto.ProductDTO;
import ppr.ru.integrationservice.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "API для работы с продуктами магазина")
public class ProductRestController {

    private final ProductService productService;

    @Operation(
            summary = "Получить список продуктов",
            description = "Возвращает список всех продуктов с возможностью фильтрации по категории и включения информации о наличии на складах"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение списка продуктов",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class))
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(
            @Parameter(description = "Фильтр по категории (например: Электроника, Бытовая техника)")
            @RequestParam(required = false) String category,
            @Parameter(description = "Включить информацию о наличии на складах")
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

    @Operation(
            summary = "Получить продукт по ID",
            description = "Возвращает информацию о продукте по его уникальному идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Продукт найден",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Продукт не найден",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(
            @Parameter(description = "Уникальный идентификатор продукта", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Включить информацию о наличии на складах")
            @RequestParam(required = false, defaultValue = "false") boolean includeAvailability) {

        return (includeAvailability
                ? productService.getProductByIdWithAvailability(id)
                : productService.getProductById(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Получить продукт по SKU",
            description = "Возвращает информацию о продукте по его артикулу (SKU)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Продукт найден",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Продукт не найден",
                    content = @Content
            )
    })
    @GetMapping("/sku/{sku}")
    public ResponseEntity<ProductDTO> getProductBySku(
            @Parameter(description = "Артикул продукта (SKU)", example = "SAM-S24-BLK")
            @PathVariable String sku,
            @Parameter(description = "Включить информацию о наличии на складах")
            @RequestParam(required = false, defaultValue = "false") boolean includeAvailability) {

        return (includeAvailability
                ? productService.getProductBySkuWithAvailability(sku)
                : productService.getProductBySku(sku))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
