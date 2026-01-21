package ppr.ru.integrationservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ppr.ru.integrationservice.model.mongo.Customer;
import ppr.ru.integrationservice.model.mongo.Order;
import ppr.ru.integrationservice.repository.mongo.CustomerRepository;
import ppr.ru.integrationservice.repository.mongo.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class MongoDataInitializer {

    @Bean
    @Profile("!test")
    public CommandLineRunner initMongoData(CustomerRepository customerRepository, OrderRepository orderRepository) {
        return args -> {
            if (customerRepository.count() > 0) {
                log.info("MongoDB данные уже существуют, пропускаем инициализацию");
                return;
            }

            log.info("Инициализация тестовых данных MongoDB...");

            // Создание клиентов
            Customer customer1 = customerRepository.save(Customer.builder()
                    .name("Иванов Иван Иванович")
                    .email("ivanov@example.com")
                    .phone("+7 (999) 123-45-67")
                    .address(Customer.Address.builder()
                            .street("ул. Пушкина, д. 10, кв. 5")
                            .city("Москва")
                            .state("Московская область")
                            .zipCode("101000")
                            .country("Россия")
                            .build())
                    .build());

            Customer customer2 = customerRepository.save(Customer.builder()
                    .name("Петрова Анна Сергеевна")
                    .email("petrova@example.com")
                    .phone("+7 (999) 234-56-78")
                    .address(Customer.Address.builder()
                            .street("пр. Невский, д. 50, кв. 12")
                            .city("Санкт-Петербург")
                            .state("Ленинградская область")
                            .zipCode("190000")
                            .country("Россия")
                            .build())
                    .build());

            Customer customer3 = customerRepository.save(Customer.builder()
                    .name("Сидоров Алексей Петрович")
                    .email("sidorov@example.com")
                    .phone("+7 (999) 345-67-89")
                    .address(Customer.Address.builder()
                            .street("ул. Ленина, д. 25, кв. 100")
                            .city("Новосибирск")
                            .state("Новосибирская область")
                            .zipCode("630000")
                            .country("Россия")
                            .build())
                    .build());

            log.info("Создано {} клиентов", customerRepository.count());

            // Создание заказов
            // Заказ 1 - Доставлен
            orderRepository.save(Order.builder()
                    .customerId(customer1.getId())
                    .items(List.of(
                            Order.OrderItem.builder()
                                    .productId(1L)
                                    .productName("Смартфон Samsung Galaxy S24")
                                    .quantity(1)
                                    .unitPrice(new BigDecimal("89990.00"))
                                    .subtotal(new BigDecimal("89990.00"))
                                    .build(),
                            Order.OrderItem.builder()
                                    .productId(3L)
                                    .productName("Наушники Sony WH-1000XM5")
                                    .quantity(1)
                                    .unitPrice(new BigDecimal("34990.00"))
                                    .subtotal(new BigDecimal("34990.00"))
                                    .build()
                    ))
                    .total(new BigDecimal("124980.00"))
                    .status(Order.OrderStatus.DELIVERED)
                    .createdAt(LocalDateTime.of(2024, 1, 5, 14, 30, 0))
                    .updatedAt(LocalDateTime.of(2024, 1, 10, 11, 0, 0))
                    .build());

            // Заказ 2 - В обработке
            orderRepository.save(Order.builder()
                    .customerId(customer1.getId())
                    .items(List.of(
                            Order.OrderItem.builder()
                                    .productId(2L)
                                    .productName("Ноутбук Apple MacBook Pro 14")
                                    .quantity(1)
                                    .unitPrice(new BigDecimal("199990.00"))
                                    .subtotal(new BigDecimal("199990.00"))
                                    .build()
                    ))
                    .total(new BigDecimal("199990.00"))
                    .status(Order.OrderStatus.PROCESSING)
                    .createdAt(LocalDateTime.of(2024, 1, 14, 10, 15, 0))
                    .updatedAt(LocalDateTime.of(2024, 1, 14, 16, 30, 0))
                    .build());

            // Заказ 3 - Подтвержден
            orderRepository.save(Order.builder()
                    .customerId(customer2.getId())
                    .items(List.of(
                            Order.OrderItem.builder()
                                    .productId(4L)
                                    .productName("Кофемашина DeLonghi Magnifica S")
                                    .quantity(1)
                                    .unitPrice(new BigDecimal("54990.00"))
                                    .subtotal(new BigDecimal("54990.00"))
                                    .build(),
                            Order.OrderItem.builder()
                                    .productId(5L)
                                    .productName("Пылесос Dyson V15 Detect")
                                    .quantity(1)
                                    .unitPrice(new BigDecimal("69990.00"))
                                    .subtotal(new BigDecimal("69990.00"))
                                    .build()
                    ))
                    .total(new BigDecimal("124980.00"))
                    .status(Order.OrderStatus.CONFIRMED)
                    .createdAt(LocalDateTime.of(2024, 1, 15, 9, 0, 0))
                    .updatedAt(LocalDateTime.of(2024, 1, 15, 9, 30, 0))
                    .build());

            // Заказ 4 - Отправлен
            orderRepository.save(Order.builder()
                    .customerId(customer2.getId())
                    .items(List.of(
                            Order.OrderItem.builder()
                                    .productId(7L)
                                    .productName("Игровая консоль Sony PlayStation 5")
                                    .quantity(1)
                                    .unitPrice(new BigDecimal("59990.00"))
                                    .subtotal(new BigDecimal("59990.00"))
                                    .build()
                    ))
                    .total(new BigDecimal("59990.00"))
                    .status(Order.OrderStatus.SHIPPED)
                    .createdAt(LocalDateTime.of(2024, 1, 12, 18, 45, 0))
                    .updatedAt(LocalDateTime.of(2024, 1, 14, 8, 0, 0))
                    .build());

            // Заказ 5 - Ожидает подтверждения
            orderRepository.save(Order.builder()
                    .customerId(customer3.getId())
                    .items(List.of(
                            Order.OrderItem.builder()
                                    .productId(6L)
                                    .productName("Телевизор LG OLED 55\"")
                                    .quantity(1)
                                    .unitPrice(new BigDecimal("129990.00"))
                                    .subtotal(new BigDecimal("129990.00"))
                                    .build(),
                            Order.OrderItem.builder()
                                    .productId(8L)
                                    .productName("Умные часы Apple Watch Ultra 2")
                                    .quantity(2)
                                    .unitPrice(new BigDecimal("79990.00"))
                                    .subtotal(new BigDecimal("159980.00"))
                                    .build()
                    ))
                    .total(new BigDecimal("289970.00"))
                    .status(Order.OrderStatus.PENDING)
                    .createdAt(LocalDateTime.of(2024, 1, 15, 12, 0, 0))
                    .updatedAt(LocalDateTime.of(2024, 1, 15, 12, 0, 0))
                    .build());

            // Заказ 6 - Отменен
            orderRepository.save(Order.builder()
                    .customerId(customer3.getId())
                    .items(List.of(
                            Order.OrderItem.builder()
                                    .productId(10L)
                                    .productName("Электросамокат Xiaomi Electric Scooter 4 Pro")
                                    .quantity(1)
                                    .unitPrice(new BigDecimal("49990.00"))
                                    .subtotal(new BigDecimal("49990.00"))
                                    .build()
                    ))
                    .total(new BigDecimal("49990.00"))
                    .status(Order.OrderStatus.CANCELLED)
                    .createdAt(LocalDateTime.of(2024, 1, 8, 15, 30, 0))
                    .updatedAt(LocalDateTime.of(2024, 1, 9, 10, 0, 0))
                    .build());

            log.info("Создано {} заказов", orderRepository.count());
            log.info("Инициализация тестовых данных MongoDB завершена");
        };
    }
}
