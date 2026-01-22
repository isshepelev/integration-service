package ppr.ru.integrationservice.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ppr.ru.integrationservice.dto.CreateCustomerRequest;
import ppr.ru.integrationservice.dto.CustomerDTO;
import ppr.ru.integrationservice.dto.UpdateCustomerRequest;
import ppr.ru.integrationservice.service.CustomerService;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CustomerGraphQLController {

    private final CustomerService customerService;

    // ==================== Queries ====================

    @QueryMapping
    public List<CustomerDTO> customers() {
        return customerService.getAllCustomers();
    }

    @QueryMapping
    public CustomerDTO customer(@Argument String id) {
        return customerService.getCustomerById(id).orElse(null);
    }

    @QueryMapping
    public CustomerDTO customerByEmail(@Argument String email) {
        return customerService.getCustomerByEmail(email).orElse(null);
    }

    // ==================== Mutations ====================

    @MutationMapping
    public CustomerDTO createCustomer(@Argument("input") Map<String, Object> input) {
        CreateCustomerRequest request = mapToCreateRequest(input);
        return customerService.createCustomer(request);
    }

    @MutationMapping
    public CustomerDTO updateCustomer(@Argument String id, @Argument("input") Map<String, Object> input) {
        UpdateCustomerRequest request = mapToUpdateRequest(input);
        return customerService.updateCustomer(id, request).orElse(null);
    }

    @MutationMapping
    public boolean deleteCustomer(@Argument String id) {
        return customerService.deleteCustomer(id);
    }

    // ==================== Helper Methods ====================

    @SuppressWarnings("unchecked")
    private CreateCustomerRequest mapToCreateRequest(Map<String, Object> input) {
        CreateCustomerRequest.CreateCustomerRequestBuilder builder = CreateCustomerRequest.builder()
                .name((String) input.get("name"))
                .email((String) input.get("email"))
                .phone((String) input.get("phone"));

        if (input.get("address") != null) {
            Map<String, String> addressMap = (Map<String, String>) input.get("address");
            builder.address(CreateCustomerRequest.AddressRequest.builder()
                    .street(addressMap.get("street"))
                    .city(addressMap.get("city"))
                    .state(addressMap.get("state"))
                    .zipCode(addressMap.get("zipCode"))
                    .country(addressMap.get("country"))
                    .build());
        }

        return builder.build();
    }

    @SuppressWarnings("unchecked")
    private UpdateCustomerRequest mapToUpdateRequest(Map<String, Object> input) {
        UpdateCustomerRequest.UpdateCustomerRequestBuilder builder = UpdateCustomerRequest.builder()
                .name((String) input.get("name"))
                .email((String) input.get("email"))
                .phone((String) input.get("phone"));

        if (input.get("address") != null) {
            Map<String, String> addressMap = (Map<String, String>) input.get("address");
            builder.address(CreateCustomerRequest.AddressRequest.builder()
                    .street(addressMap.get("street"))
                    .city(addressMap.get("city"))
                    .state(addressMap.get("state"))
                    .zipCode(addressMap.get("zipCode"))
                    .country(addressMap.get("country"))
                    .build());
        }

        return builder.build();
    }
}
