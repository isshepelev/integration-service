package ppr.ru.integrationservice.service;

import ppr.ru.integrationservice.dto.CreateOrderRequest;
import ppr.ru.integrationservice.dto.OrderDTO;
import ppr.ru.integrationservice.dto.UpdateOrderStatusRequest;
import ppr.ru.integrationservice.model.mongo.Order.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<OrderDTO> getAllOrders();

    Optional<OrderDTO> getOrderById(String id);

    List<OrderDTO> getOrdersByCustomerId(String customerId);

    List<OrderDTO> getOrdersByStatus(OrderStatus status);

    OrderDTO createOrder(CreateOrderRequest request);

    Optional<OrderDTO> updateOrderStatus(String id, UpdateOrderStatusRequest request);

    boolean deleteOrder(String id);
}
