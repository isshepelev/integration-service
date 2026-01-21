package ppr.ru.integrationservice.service;

import ppr.ru.integrationservice.dto.CreateCustomerRequest;
import ppr.ru.integrationservice.dto.CustomerDTO;
import ppr.ru.integrationservice.dto.UpdateCustomerRequest;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<CustomerDTO> getAllCustomers();

    Optional<CustomerDTO> getCustomerById(String id);

    Optional<CustomerDTO> getCustomerByEmail(String email);

    CustomerDTO createCustomer(CreateCustomerRequest request);

    Optional<CustomerDTO> updateCustomer(String id, UpdateCustomerRequest request);

    Optional<CustomerDTO> partialUpdateCustomer(String id, UpdateCustomerRequest request);

    boolean deleteCustomer(String id);
}
