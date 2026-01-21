package ppr.ru.integrationservice.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ppr.ru.integrationservice.model.mongo.Order;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByCustomerId(String customerId);

    List<Order> findByStatus(Order.OrderStatus status);
}
