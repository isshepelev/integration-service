package ppr.ru.integrationservice.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ppr.ru.integrationservice.model.mongo.Order;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDTO toDTO(Order order);

    OrderDTO.OrderItemDTO toOrderItemDTO(Order.OrderItem item);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "total", source = "items", qualifiedByName = "calculateTotal")
    @Mapping(target = "items", source = "items", qualifiedByName = "toOrderItems")
    Order toEntity(CreateOrderRequest request);

    @Named("toOrderItems")
    default List<Order.OrderItem> toOrderItems(List<CreateOrderRequest.OrderItemRequest> items) {
        if (items == null) {
            return List.of();
        }
        return items.stream()
                .map(this::toOrderItem)
                .toList();
    }

    default Order.OrderItem toOrderItem(CreateOrderRequest.OrderItemRequest request) {
        BigDecimal subtotal = request.getUnitPrice().multiply(BigDecimal.valueOf(request.getQuantity()));
        return Order.OrderItem.builder()
                .productId(request.getProductId())
                .productName(request.getProductName())
                .quantity(request.getQuantity())
                .unitPrice(request.getUnitPrice())
                .subtotal(subtotal)
                .build();
    }

    @Named("calculateTotal")
    default BigDecimal calculateTotal(List<CreateOrderRequest.OrderItemRequest> items) {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return items.stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
