package ppr.ru.integrationservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Информация о продукте")
public class ProductDTO {

    @Schema(description = "Уникальный идентификатор продукта", example = "1")
    private Long id;

    @Schema(description = "Название продукта", example = "Смартфон Samsung Galaxy S24")
    private String name;

    @Schema(description = "Описание продукта", example = "Флагманский смартфон с AI функциями")
    private String description;

    @Schema(description = "Цена продукта в рублях", example = "89990.00")
    private BigDecimal price;

    @Schema(description = "Артикул (SKU) продукта", example = "SAM-S24-BLK")
    private String sku;

    @Schema(description = "Категория продукта", example = "Электроника")
    private String category;

    @Schema(description = "Информация о наличии на складах (заполняется при includeAvailability=true)")
    private List<AvailabilityDTO> availabilities;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "Информация о наличии продукта на складе")
    public static class AvailabilityDTO {

        @Schema(description = "Уникальный идентификатор записи о наличии", example = "1")
        private Long id;

        @Schema(description = "Количество единиц на складе", example = "50")
        private Integer quantity;

        @Schema(description = "Название склада", example = "Москва-Центр")
        private String warehouse;

        @Schema(description = "Дата и время последнего обновления", example = "2024-01-15T10:30:00")
        private LocalDateTime lastUpdated;
    }
}
