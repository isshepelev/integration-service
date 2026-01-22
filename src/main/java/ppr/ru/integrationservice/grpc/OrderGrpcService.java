package ppr.ru.integrationservice.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ppr.ru.integrationservice.dto.CreateOrderRequest;
import ppr.ru.integrationservice.dto.OrderDTO;
import ppr.ru.integrationservice.dto.UpdateOrderStatusRequest;
import ppr.ru.integrationservice.grpc.generated.*;
import ppr.ru.integrationservice.service.OrderService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@GrpcService
@RequiredArgsConstructor
public class OrderGrpcService extends OrderServiceGrpc.OrderServiceImplBase {

    private final OrderService orderService;

    // ==================== Query Operations ====================

    @Override
    public void getOrders(GetOrdersRequest request, StreamObserver<GetOrdersResponse> responseObserver) {
        List<OrderDTO> orders = orderService.getAllOrders();

        GetOrdersResponse response = GetOrdersResponse.newBuilder()
                .addAllOrders(orders.stream().map(this::toProto).toList())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getOrderById(GetOrderByIdRequest request, StreamObserver<OrderResponse> responseObserver) {
        Optional<OrderDTO> order = orderService.getOrderById(request.getId());

        OrderResponse.Builder builder = OrderResponse.newBuilder()
                .setFound(order.isPresent());

        order.ifPresent(o -> builder.setOrder(toProto(o)));

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getOrdersByCustomerId(GetOrdersByCustomerIdRequest request, StreamObserver<GetOrdersResponse> responseObserver) {
        List<OrderDTO> orders = orderService.getOrdersByCustomerId(request.getCustomerId());

        GetOrdersResponse response = GetOrdersResponse.newBuilder()
                .addAllOrders(orders.stream().map(this::toProto).toList())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getOrdersByStatus(GetOrdersByStatusRequest request, StreamObserver<GetOrdersResponse> responseObserver) {
        ppr.ru.integrationservice.model.mongo.Order.OrderStatus status = toModelStatus(request.getStatus());
        List<OrderDTO> orders = orderService.getOrdersByStatus(status);

        GetOrdersResponse response = GetOrdersResponse.newBuilder()
                .addAllOrders(orders.stream().map(this::toProto).toList())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    // ==================== Mutation Operations ====================

    @Override
    public void createOrder(ppr.ru.integrationservice.grpc.generated.CreateOrderRequest request,
                            StreamObserver<OrderResponse> responseObserver) {
        List<CreateOrderRequest.OrderItemRequest> items = request.getItemsList().stream()
                .map(item -> CreateOrderRequest.OrderItemRequest.builder()
                        .productId(item.getProductId())
                        .productName(item.getProductName())
                        .quantity(item.getQuantity())
                        .unitPrice(BigDecimal.valueOf(item.getUnitPrice()))
                        .build())
                .toList();

        CreateOrderRequest serviceRequest = CreateOrderRequest.builder()
                .customerId(request.getCustomerId())
                .items(items)
                .build();

        OrderDTO created = orderService.createOrder(serviceRequest);

        OrderResponse response = OrderResponse.newBuilder()
                .setOrder(toProto(created))
                .setFound(true)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateOrderStatus(ppr.ru.integrationservice.grpc.generated.UpdateOrderStatusRequest request,
                                  StreamObserver<OrderResponse> responseObserver) {
        UpdateOrderStatusRequest serviceRequest = UpdateOrderStatusRequest.builder()
                .status(toModelStatus(request.getStatus()))
                .build();

        Optional<OrderDTO> updated = orderService.updateOrderStatus(request.getId(), serviceRequest);

        OrderResponse.Builder builder = OrderResponse.newBuilder()
                .setFound(updated.isPresent());

        updated.ifPresent(o -> builder.setOrder(toProto(o)));

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteOrder(DeleteOrderRequest request, StreamObserver<DeleteOrderResponse> responseObserver) {
        boolean success = orderService.deleteOrder(request.getId());

        DeleteOrderResponse response = DeleteOrderResponse.newBuilder()
                .setSuccess(success)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    // ==================== Helper Methods ====================

    private Order toProto(OrderDTO dto) {
        Order.Builder builder = Order.newBuilder()
                .setId(dto.getId())
                .setCustomerId(dto.getCustomerId())
                .setTotal(dto.getTotal().doubleValue())
                .setStatus(toProtoStatus(dto.getStatus()));

        if (dto.getItems() != null) {
            builder.addAllItems(dto.getItems().stream().map(this::toProtoItem).toList());
        }

        if (dto.getCreatedAt() != null) {
            builder.setCreatedAt(dto.getCreatedAt().toString());
        }

        if (dto.getUpdatedAt() != null) {
            builder.setUpdatedAt(dto.getUpdatedAt().toString());
        }

        return builder.build();
    }

    private OrderItem toProtoItem(OrderDTO.OrderItemDTO dto) {
        return OrderItem.newBuilder()
                .setProductId(dto.getProductId())
                .setProductName(dto.getProductName())
                .setQuantity(dto.getQuantity())
                .setUnitPrice(dto.getUnitPrice().doubleValue())
                .setSubtotal(dto.getSubtotal().doubleValue())
                .build();
    }

    private OrderStatus toProtoStatus(ppr.ru.integrationservice.model.mongo.Order.OrderStatus status) {
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
            case UNRECOGNIZED -> ppr.ru.integrationservice.model.mongo.Order.OrderStatus.PENDING;
        };
    }
}
