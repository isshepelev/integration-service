package ppr.ru.integrationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ppr.ru.integrationservice.dto.CreateCustomerRequest;
import ppr.ru.integrationservice.dto.CustomerDTO;
import ppr.ru.integrationservice.dto.CustomerMapper;
import ppr.ru.integrationservice.dto.UpdateCustomerRequest;
import ppr.ru.integrationservice.model.mongo.Customer;
import ppr.ru.integrationservice.repository.mongo.CustomerRepository;
import ppr.ru.integrationservice.service.CustomerService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toDTO)
                .toList();
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(String id) {
        return customerRepository.findById(id)
                .map(customerMapper::toDTO);
    }

    @Override
    public Optional<CustomerDTO> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .map(customerMapper::toDTO);
    }

    @Override
    public CustomerDTO createCustomer(CreateCustomerRequest request) {
        Customer customer = customerMapper.toEntity(request);
        Customer saved = customerRepository.save(customer);
        return customerMapper.toDTO(saved);
    }

    @Override
    public Optional<CustomerDTO> updateCustomer(String id, UpdateCustomerRequest request) {
        return customerRepository.findById(id)
                .map(customer -> {
                    if (request.getName() != null) customer.setName(request.getName());
                    if (request.getEmail() != null) customer.setEmail(request.getEmail());
                    if (request.getPhone() != null) customer.setPhone(request.getPhone());
                    if (request.getAddress() != null) {
                        customer.setAddress(customerMapper.toAddress(request.getAddress()));
                    }
                    return customerMapper.toDTO(customerRepository.save(customer));
                });
    }

    @Override
    public Optional<CustomerDTO> partialUpdateCustomer(String id, UpdateCustomerRequest request) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customerMapper.updateEntity(request, customer);
                    return customerMapper.toDTO(customerRepository.save(customer));
                });
    }

    @Override
    public boolean deleteCustomer(String id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
