package ppr.ru.integrationservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Запрос на создание заказа")
public class CreateOrderRequest {

    @NotBlank(message = "Идентификатор клиента обязателен")
    @Schema(description = "Идентификатор клиента", example = "507f1f77bcf86cd799439011", required = true)
    private String customerId;

    @NotEmpty(message = "Заказ должен содержать хотя бы одну позицию")
    @Valid
    @Schema(description = "Позиции заказа", required = true)
    private List<OrderItemRequest> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "Позиция заказа")
    public static class OrderItemRequest {

        @NotNull(message = "Идентификатор продукта обязателен")
        @Schema(description = "Идентификатор продукта", example = "1", required = true)
        private Long productId;

        @NotBlank(message = "Название продукта обязательно")
        @Schema(description = "Название продукта", example = "Смартфон Samsung Galaxy S24", required = true)
        private String productName;

        @NotNull(message = "Количество обязательно")
        @Min(value = 1, message = "Количество должно быть не менее 1")
        @Schema(description = "Количество", example = "2", required = true)
        private Integer quantity;

        @NotNull(message = "Цена за единицу обязательна")
        @Schema(description = "Цена за единицу", example = "89990.00", required = true)
        private BigDecimal unitPrice;
    }
}
