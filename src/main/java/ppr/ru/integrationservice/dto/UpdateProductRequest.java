package ppr.ru.integrationservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
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
@Schema(description = "Запрос на обновление продукта")
public class UpdateProductRequest {

    @Size(max = 255, message = "Название продукта не должно превышать 255 символов")
    @Schema(description = "Название продукта", example = "Смартфон Samsung Galaxy S24 Ultra")
    private String name;

    @Schema(description = "Описание продукта", example = "Обновленное описание продукта")
    private String description;

    @DecimalMin(value = "0.01", message = "Цена должна быть больше 0")
    @Schema(description = "Цена продукта в рублях", example = "99990.00")
    private BigDecimal price;

    @Size(max = 100, message = "SKU не должен превышать 100 символов")
    @Schema(description = "Артикул (SKU) продукта", example = "SAM-S24U-BLK")
    private String sku;

    @Size(max = 100, message = "Категория не должна превышать 100 символов")
    @Schema(description = "Категория продукта", example = "Электроника")
    private String category;
}
