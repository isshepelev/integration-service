package ppr.ru.integrationservice.soap.endpoint;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ppr.ru.integrationservice.dto.CreateCustomerRequest;
import ppr.ru.integrationservice.dto.CustomerDTO;
import ppr.ru.integrationservice.dto.UpdateCustomerRequest;
import ppr.ru.integrationservice.service.CustomerService;
import ppr.ru.integrationservice.soap.generated.*;

import java.util.List;
import java.util.Optional;

@Endpoint
@RequiredArgsConstructor
public class CustomerSoapEndpoint {

    private static final String NAMESPACE_URI = "http://integrationservice.ru.ppr/soap/customers";

    private final CustomerService customerService;

    // ==================== Query Operations ====================

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCustomersRequest")
    @ResponsePayload
    public GetCustomersResponse getCustomers(@RequestPayload GetCustomersRequest request) {
        List<CustomerDTO> customers = customerService.getAllCustomers();

        GetCustomersResponse response = new GetCustomersResponse();
        customers.forEach(dto -> response.getCustomer().add(toSoapCustomer(dto)));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCustomerByIdRequest")
    @ResponsePayload
    public GetCustomerResponse getCustomerById(@RequestPayload GetCustomerByIdRequest request) {
        Optional<CustomerDTO> customer = customerService.getCustomerById(request.getId());

        GetCustomerResponse response = new GetCustomerResponse();
        customer.ifPresent(dto -> response.setCustomer(toSoapCustomer(dto)));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCustomerByEmailRequest")
    @ResponsePayload
    public GetCustomerResponse getCustomerByEmail(@RequestPayload GetCustomerByEmailRequest request) {
        Optional<CustomerDTO> customer = customerService.getCustomerByEmail(request.getEmail());

        GetCustomerResponse response = new GetCustomerResponse();
        customer.ifPresent(dto -> response.setCustomer(toSoapCustomer(dto)));
        return response;
    }

    // ==================== Mutation Operations ====================

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createCustomerRequest")
    @ResponsePayload
    public CreateCustomerResponse createCustomer(@RequestPayload ppr.ru.integrationservice.soap.generated.CreateCustomerRequest request) {
        CreateCustomerRequest serviceRequest = CreateCustomerRequest.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(toAddressRequest(request.getAddress()))
                .build();

        CustomerDTO created = customerService.createCustomer(serviceRequest);

        CreateCustomerResponse response = new CreateCustomerResponse();
        response.setCustomer(toSoapCustomer(created));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateCustomerRequest")
    @ResponsePayload
    public UpdateCustomerResponse updateCustomer(@RequestPayload ppr.ru.integrationservice.soap.generated.UpdateCustomerRequest request) {
        UpdateCustomerRequest serviceRequest = UpdateCustomerRequest.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(toAddressRequest(request.getAddress()))
                .build();

        Optional<CustomerDTO> updated = customerService.updateCustomer(request.getId(), serviceRequest);

        UpdateCustomerResponse response = new UpdateCustomerResponse();
        updated.ifPresent(dto -> response.setCustomer(toSoapCustomer(dto)));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteCustomerRequest")
    @ResponsePayload
    public DeleteCustomerResponse deleteCustomer(@RequestPayload DeleteCustomerRequest request) {
        boolean success = customerService.deleteCustomer(request.getId());

        DeleteCustomerResponse response = new DeleteCustomerResponse();
        response.setSuccess(success);
        return response;
    }

    // ==================== Helper Methods ====================

    private Customer toSoapCustomer(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());

        if (dto.getAddress() != null) {
            customer.setAddress(toSoapAddress(dto.getAddress()));
        }

        return customer;
    }

    private Address toSoapAddress(CustomerDTO.AddressDTO dto) {
        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getZipCode());
        address.setCountry(dto.getCountry());
        return address;
    }

    private CreateCustomerRequest.AddressRequest toAddressRequest(AddressInput input) {
        if (input == null) {
            return null;
        }

        return CreateCustomerRequest.AddressRequest.builder()
                .street(input.getStreet())
                .city(input.getCity())
                .state(input.getState())
                .zipCode(input.getZipCode())
                .country(input.getCountry())
                .build();
    }
}
