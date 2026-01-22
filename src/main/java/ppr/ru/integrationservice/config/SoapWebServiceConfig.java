package ppr.ru.integrationservice.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class SoapWebServiceConfig {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    // ==================== Products WSDL (PostgreSQL) ====================

    @Bean(name = "products")
    public DefaultWsdl11Definition productsWsdl11Definition(XsdSchema productsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("ProductsPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://integrationservice.ru.ppr/soap/products");
        wsdl11Definition.setSchema(productsSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema productsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/products.xsd"));
    }

    // ==================== Customers WSDL (MongoDB) ====================

    @Bean(name = "customers")
    public DefaultWsdl11Definition customersWsdl11Definition(XsdSchema customersSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("CustomersPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://integrationservice.ru.ppr/soap/customers");
        wsdl11Definition.setSchema(customersSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema customersSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/customers.xsd"));
    }

    // ==================== Orders WSDL (MongoDB) ====================

    @Bean(name = "orders")
    public DefaultWsdl11Definition ordersWsdl11Definition(XsdSchema ordersSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("OrdersPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://integrationservice.ru.ppr/soap/orders");
        wsdl11Definition.setSchema(ordersSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema ordersSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/orders.xsd"));
    }

    // ==================== Integration WSDL (Combined) ====================

    @Bean(name = "integration")
    public DefaultWsdl11Definition integrationWsdl11Definition(XsdSchema integrationSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("IntegrationPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://integrationservice.ru.ppr/soap/integration");
        wsdl11Definition.setSchema(integrationSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema integrationSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/integration.xsd"));
    }
}
