package ppr.ru.integrationservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ppr.ru.integrationservice.model.mongo.Order.OrderStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Запрос на обновление статуса заказа")
public class UpdateOrderStatusRequest {

    @NotNull(message = "Статус обязателен")
    @Schema(description = "Новый статус заказа", example = "CONFIRMED", required = true)
    private OrderStatus status;
}
