package ppr.ru.integrationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ppr.ru.integrationservice.dto.CreateOrderRequest;
import ppr.ru.integrationservice.dto.OrderDTO;
import ppr.ru.integrationservice.dto.OrderMapper;
import ppr.ru.integrationservice.dto.UpdateOrderStatusRequest;
import ppr.ru.integrationservice.model.mongo.Order;
import ppr.ru.integrationservice.model.mongo.Order.OrderStatus;
import ppr.ru.integrationservice.repository.mongo.OrderRepository;
import ppr.ru.integrationservice.service.OrderService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    @Override
    public Optional<OrderDTO> getOrderById(String id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDTO);
    }

    @Override
    public List<OrderDTO> getOrdersByCustomerId(String customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    @Override
    public List<OrderDTO> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status).stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    @Override
    public OrderDTO createOrder(CreateOrderRequest request) {
        Order order = orderMapper.toEntity(request);
        Order saved = orderRepository.save(order);
        return orderMapper.toDTO(saved);
    }

    @Override
    public Optional<OrderDTO> updateOrderStatus(String id, UpdateOrderStatusRequest request) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setStatus(request.getStatus());
                    return orderMapper.toDTO(orderRepository.save(order));
                });
    }

    @Override
    public boolean deleteOrder(String id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
