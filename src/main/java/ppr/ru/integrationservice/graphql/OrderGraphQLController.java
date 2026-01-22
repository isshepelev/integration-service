package ppr.ru.integrationservice.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ppr.ru.integrationservice.dto.CreateOrderRequest;
import ppr.ru.integrationservice.dto.OrderDTO;
import ppr.ru.integrationservice.dto.UpdateOrderStatusRequest;
import ppr.ru.integrationservice.model.mongo.Order.OrderStatus;
import ppr.ru.integrationservice.service.OrderService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class OrderGraphQLController {

    private final OrderService orderService;

    // ==================== Queries ====================

    @QueryMapping
    public List<OrderDTO> orders() {
        return orderService.getAllOrders();
    }

    @QueryMapping
    public OrderDTO order(@Argument String id) {
        return orderService.getOrderById(id).orElse(null);
    }

    @QueryMapping
    public List<OrderDTO> ordersByCustomerId(@Argument String customerId) {
        return orderService.getOrdersByCustomerId(customerId);
    }

    @QueryMapping
    public List<OrderDTO> ordersByStatus(@Argument OrderStatus status) {
        return orderService.getOrdersByStatus(status);
    }

    // ==================== Mutations ====================

    @MutationMapping
    public OrderDTO createOrder(@Argument("input") Map<String, Object> input) {
        CreateOrderRequest request = mapToCreateRequest(input);
        return orderService.createOrder(request);
    }

    @MutationMapping
    public OrderDTO updateOrderStatus(@Argument String id, @Argument OrderStatus status) {
        UpdateOrderStatusRequest request = UpdateOrderStatusRequest.builder()
                .status(status)
                .build();
        return orderService.updateOrderStatus(id, request).orElse(null);
    }

    @MutationMapping
    public boolean deleteOrder(@Argument String id) {
        return orderService.deleteOrder(id);
    }

    // ==================== Helper Methods ====================

    @SuppressWarnings("unchecked")
    private CreateOrderRequest mapToCreateRequest(Map<String, Object> input) {
        List<Map<String, Object>> itemsList = (List<Map<String, Object>>) input.get("items");

        List<CreateOrderRequest.OrderItemRequest> items = itemsList.stream()
                .map(itemMap -> CreateOrderRequest.OrderItemRequest.builder()
                        .productId(((Number) itemMap.get("productId")).longValue())
                        .productName((String) itemMap.get("productName"))
                        .quantity(((Number) itemMap.get("quantity")).intValue())
                        .unitPrice(new BigDecimal(itemMap.get("unitPrice").toString()))
                        .build())
                .toList();

        return CreateOrderRequest.builder()
                .customerId((String) input.get("customerId"))
                .items(items)
                .build();
    }
}
