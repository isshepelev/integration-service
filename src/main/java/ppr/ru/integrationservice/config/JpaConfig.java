package ppr.ru.integrationservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "ppr.ru.integrationservice.repository.jpa")
public class JpaConfig {
}
