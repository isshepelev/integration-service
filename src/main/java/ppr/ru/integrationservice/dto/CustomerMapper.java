package ppr.ru.integrationservice.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ppr.ru.integrationservice.model.mongo.Customer;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerMapper {

    CustomerDTO toDTO(Customer customer);

    CustomerDTO.AddressDTO toAddressDTO(Customer.Address address);

    @Mapping(target = "id", ignore = true)
    Customer toEntity(CreateCustomerRequest request);

    Customer.Address toAddress(CreateCustomerRequest.AddressRequest request);

    @Mapping(target = "id", ignore = true)
    void updateEntity(UpdateCustomerRequest request, @MappingTarget Customer customer);
}
