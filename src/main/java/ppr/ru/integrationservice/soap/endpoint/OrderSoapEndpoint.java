package ppr.ru.integrationservice.soap.endpoint;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ppr.ru.integrationservice.dto.CreateOrderRequest;
import ppr.ru.integrationservice.dto.OrderDTO;
import ppr.ru.integrationservice.dto.UpdateOrderStatusRequest;
import ppr.ru.integrationservice.service.OrderService;
import ppr.ru.integrationservice.soap.generated.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Endpoint
@RequiredArgsConstructor
public class OrderSoapEndpoint {

    private static final String NAMESPACE_URI = "http://integrationservice.ru.ppr/soap/orders";

    private final OrderService orderService;

    // ==================== Query Operations ====================

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getOrdersRequest")
    @ResponsePayload
    public GetOrdersResponse getOrders(@RequestPayload GetOrdersRequest request) {
        List<OrderDTO> orders = orderService.getAllOrders();

        GetOrdersResponse response = new GetOrdersResponse();
        orders.forEach(dto -> response.getOrder().add(toSoapOrder(dto)));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getOrderByIdRequest")
    @ResponsePayload
    public GetOrderResponse getOrderById(@RequestPayload GetOrderByIdRequest request) {
        Optional<OrderDTO> order = orderService.getOrderById(request.getId());

        GetOrderResponse response = new GetOrderResponse();
        order.ifPresent(dto -> response.setOrder(toSoapOrder(dto)));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getOrdersByCustomerIdRequest")
    @ResponsePayload
    public GetOrdersResponse getOrdersByCustomerId(@RequestPayload GetOrdersByCustomerIdRequest request) {
        List<OrderDTO> orders = orderService.getOrdersByCustomerId(request.getCustomerId());

        GetOrdersResponse response = new GetOrdersResponse();
        orders.forEach(dto -> response.getOrder().add(toSoapOrder(dto)));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getOrdersByStatusRequest")
    @ResponsePayload
    public GetOrdersResponse getOrdersByStatus(@RequestPayload GetOrdersByStatusRequest request) {
        ppr.ru.integrationservice.model.mongo.Order.OrderStatus status = toModelStatus(request.getStatus());
        List<OrderDTO> orders = orderService.getOrdersByStatus(status);

        GetOrdersResponse response = new GetOrdersResponse();
        orders.forEach(dto -> response.getOrder().add(toSoapOrder(dto)));
        return response;
    }

    // ==================== Mutation Operations ====================

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createOrderRequest")
    @ResponsePayload
    public CreateOrderResponse createOrder(@RequestPayload ppr.ru.integrationservice.soap.generated.CreateOrderRequest request) {
        List<CreateOrderRequest.OrderItemRequest> items = request.getItem().stream()
                .map(item -> CreateOrderRequest.OrderItemRequest.builder()
                        .productId(item.getProductId())
                        .productName(item.getProductName())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .build())
                .toList();

        CreateOrderRequest serviceRequest = CreateOrderRequest.builder()
                .customerId(request.getCustomerId())
                .items(items)
                .build();

        OrderDTO created = orderService.createOrder(serviceRequest);

        CreateOrderResponse response = new CreateOrderResponse();
        response.setOrder(toSoapOrder(created));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateOrderStatusRequest")
    @ResponsePayload
    public UpdateOrderStatusResponse updateOrderStatus(@RequestPayload ppr.ru.integrationservice.soap.generated.UpdateOrderStatusRequest request) {
        UpdateOrderStatusRequest serviceRequest = UpdateOrderStatusRequest.builder()
                .status(toModelStatus(request.getStatus()))
                .build();

        Optional<OrderDTO> updated = orderService.updateOrderStatus(request.getId(), serviceRequest);

        UpdateOrderStatusResponse response = new UpdateOrderStatusResponse();
        updated.ifPresent(dto -> response.setOrder(toSoapOrder(dto)));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteOrderRequest")
    @ResponsePayload
    public DeleteOrderResponse deleteOrder(@RequestPayload DeleteOrderRequest request) {
        boolean success = orderService.deleteOrder(request.getId());

        DeleteOrderResponse response = new DeleteOrderResponse();
        response.setSuccess(success);
        return response;
    }

    // ==================== Helper Methods ====================

    private Order toSoapOrder(OrderDTO dto) {
        Order order = new Order();
        order.setId(dto.getId());
        order.setCustomerId(dto.getCustomerId());
        order.setTotal(dto.getTotal());
        order.setStatus(toSoapStatus(dto.getStatus()));

        if (dto.getItems() != null) {
            dto.getItems().forEach(item -> order.getItem().add(toSoapOrderItem(item)));
        }

        if (dto.getCreatedAt() != null) {
            order.setCreatedAt(dto.getCreatedAt().toString());
        }

        if (dto.getUpdatedAt() != null) {
            order.setUpdatedAt(dto.getUpdatedAt().toString());
        }

        return order;
    }

    private OrderItem toSoapOrderItem(OrderDTO.OrderItemDTO dto) {
        OrderItem item = new OrderItem();
        item.setProductId(dto.getProductId());
        item.setProductName(dto.getProductName());
        item.setQuantity(dto.getQuantity());
        item.setUnitPrice(dto.getUnitPrice());
        item.setSubtotal(dto.getSubtotal());
        return item;
    }

    private OrderStatus toSoapStatus(ppr.ru.integrationservice.model.mongo.Order.OrderStatus status) {
        return switch (status) {
            case PENDING -> OrderStatus.PENDING;
            case CONFIRMED -> OrderStatus.CONFIRMED;
            case PROCESSING -> OrderStatus.PROCESSING;
            case SHIPPED -> OrderStatus.SHIPPED;
            case DELIVERED -> OrderStatus.DELIVERED;
            case CANCELLED -> OrderStatus.CANCELLED;
        };
    }

    private ppr.ru.integrationservice.model.mongo.Order.OrderStatus toModelStatus(OrderStatus status) {
        return switch (status) {
            case PENDING -> ppr.ru.integrationservice.model.mongo.Order.OrderStatus.PENDING;
            case CONFIRMED -> ppr.ru.integrationservice.model.mongo.Order.OrderStatus.CONFIRMED;
            case PROCESSING -> ppr.ru.integrationservice.model.mongo.Order.OrderStatus.PROCESSING;
            case SHIPPED -> ppr.ru.integrationservice.model.mongo.Order.OrderStatus.SHIPPED;
            case DELIVERED -> ppr.ru.integrationservice.model.mongo.Order.OrderStatus.DELIVERED;
            case CANCELLED -> ppr.ru.integrationservice.model.mongo.Order.OrderStatus.CANCELLED;
        };
    }
}
