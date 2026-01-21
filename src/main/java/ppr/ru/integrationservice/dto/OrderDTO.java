package ppr.ru.integrationservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ppr.ru.integrationservice.model.mongo.Order.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Информация о заказе")
public class OrderDTO {

    @Schema(description = "Уникальный идентификатор заказа", example = "507f1f77bcf86cd799439012")
    private String id;

    @Schema(description = "Идентификатор клиента", example = "507f1f77bcf86cd799439011")
    private String customerId;

    @Schema(description = "Позиции заказа")
    private List<OrderItemDTO> items;

    @Schema(description = "Общая сумма заказа", example = "179980.00")
    private BigDecimal total;

    @Schema(description = "Статус заказа", example = "PENDING")
    private OrderStatus status;

    @Schema(description = "Дата создания заказа", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Дата последнего обновления", example = "2024-01-15T12:00:00")
    private LocalDateTime updatedAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "Позиция заказа")
    public static class OrderItemDTO {

        @Schema(description = "Идентификатор продукта", example = "1")
        private Long productId;

        @Schema(description = "Название продукта", example = "Смартфон Samsung Galaxy S24")
        private String productName;

        @Schema(description = "Количество", example = "2")
        private Integer quantity;

        @Schema(description = "Цена за единицу", example = "89990.00")
        private BigDecimal unitPrice;

        @Schema(description = "Сумма позиции", example = "179980.00")
        private BigDecimal subtotal;
    }
}
