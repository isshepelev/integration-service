package ppr.ru.integrationservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Integration Service API")
                        .version("1.0.0")
                        .description("""
                                REST API для Integration Service.

                                Этот сервис предоставляет доступ к данным магазина через несколько протоколов:
                                - **REST API** (данная документация)
                                - **GraphQL** - /graphql (IDE: /graphiql)
                                - **gRPC** - порт 9090
                                - **SOAP** - /ws (WSDL: /ws/products.wsdl)

                                ### Основные возможности:
                                - Получение списка продуктов
                                - Фильтрация по категориям
                                - Информация о наличии на складах
                                """)
                        .contact(new Contact()
                                .name("Команда разработки")
                                .email("dev@example.com"))
                        .license(new License()
                                .name("Проприетарная лицензия")
                                .url("https://example.com/license")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Локальный сервер разработки")))
                .tags(List.of(
                        new Tag()
                                .name("Products")
                                .description("Операции с продуктами")));
    }
}
