package ppr.ru.integrationservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Запрос на создание продукта")
public class CreateProductRequest {

    @NotBlank(message = "Название продукта обязательно")
    @Size(max = 255, message = "Название продукта не должно превышать 255 символов")
    @Schema(description = "Название продукта", example = "Смартфон Samsung Galaxy S24", required = true)
    private String name;

    @Schema(description = "Описание продукта", example = "Флагманский смартфон с AI функциями")
    private String description;

    @NotNull(message = "Цена обязательна")
    @DecimalMin(value = "0.01", message = "Цена должна быть больше 0")
    @Schema(description = "Цена продукта в рублях", example = "89990.00", required = true)
    private BigDecimal price;

    @NotBlank(message = "Артикул (SKU) обязателен")
    @Size(max = 100, message = "SKU не должен превышать 100 символов")
    @Schema(description = "Артикул (SKU) продукта", example = "SAM-S24-BLK", required = true)
    private String sku;

    @Size(max = 100, message = "Категория не должна превышать 100 символов")
    @Schema(description = "Категория продукта", example = "Электроника")
    private String category;
}
