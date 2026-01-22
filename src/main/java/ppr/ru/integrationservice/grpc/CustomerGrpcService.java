package ppr.ru.integrationservice.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ppr.ru.integrationservice.dto.CreateCustomerRequest;
import ppr.ru.integrationservice.dto.CustomerDTO;
import ppr.ru.integrationservice.dto.UpdateCustomerRequest;
import ppr.ru.integrationservice.grpc.generated.*;
import ppr.ru.integrationservice.service.CustomerService;

import java.util.List;
import java.util.Optional;

@GrpcService
@RequiredArgsConstructor
public class CustomerGrpcService extends CustomerServiceGrpc.CustomerServiceImplBase {

    private final CustomerService customerService;

    // ==================== Query Operations ====================

    @Override
    public void getCustomers(GetCustomersRequest request, StreamObserver<GetCustomersResponse> responseObserver) {
        List<CustomerDTO> customers = customerService.getAllCustomers();

        GetCustomersResponse response = GetCustomersResponse.newBuilder()
                .addAllCustomers(customers.stream().map(this::toProto).toList())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getCustomerById(GetCustomerByIdRequest request, StreamObserver<CustomerResponse> responseObserver) {
        Optional<CustomerDTO> customer = customerService.getCustomerById(request.getId());

        CustomerResponse.Builder builder = CustomerResponse.newBuilder()
                .setFound(customer.isPresent());

        customer.ifPresent(c -> builder.setCustomer(toProto(c)));

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getCustomerByEmail(GetCustomerByEmailRequest request, StreamObserver<CustomerResponse> responseObserver) {
        Optional<CustomerDTO> customer = customerService.getCustomerByEmail(request.getEmail());

        CustomerResponse.Builder builder = CustomerResponse.newBuilder()
                .setFound(customer.isPresent());

        customer.ifPresent(c -> builder.setCustomer(toProto(c)));

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    // ==================== Mutation Operations ====================

    @Override
    public void createCustomer(ppr.ru.integrationservice.grpc.generated.CreateCustomerRequest request,
                               StreamObserver<CustomerResponse> responseObserver) {
        CreateCustomerRequest serviceRequest = CreateCustomerRequest.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(toAddressRequest(request.getAddress()))
                .build();

        CustomerDTO created = customerService.createCustomer(serviceRequest);

        CustomerResponse response = CustomerResponse.newBuilder()
                .setCustomer(toProto(created))
                .setFound(true)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateCustomer(ppr.ru.integrationservice.grpc.generated.UpdateCustomerRequest request,
                               StreamObserver<CustomerResponse> responseObserver) {
        UpdateCustomerRequest serviceRequest = UpdateCustomerRequest.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(toAddressRequest(request.getAddress()))
                .build();

        Optional<CustomerDTO> updated = customerService.updateCustomer(request.getId(), serviceRequest);

        CustomerResponse.Builder builder = CustomerResponse.newBuilder()
                .setFound(updated.isPresent());

        updated.ifPresent(c -> builder.setCustomer(toProto(c)));

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteCustomer(DeleteCustomerRequest request, StreamObserver<DeleteCustomerResponse> responseObserver) {
        boolean success = customerService.deleteCustomer(request.getId());

        DeleteCustomerResponse response = DeleteCustomerResponse.newBuilder()
                .setSuccess(success)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    // ==================== Helper Methods ====================

    private Customer toProto(CustomerDTO dto) {
        Customer.Builder builder = Customer.newBuilder()
                .setId(dto.getId())
                .setName(dto.getName())
                .setEmail(dto.getEmail());

        if (dto.getPhone() != null) {
            builder.setPhone(dto.getPhone());
        }

        if (dto.getAddress() != null) {
            builder.setAddress(toProtoAddress(dto.getAddress()));
        }

        return builder.build();
    }

    private Address toProtoAddress(CustomerDTO.AddressDTO dto) {
        Address.Builder builder = Address.newBuilder();

        if (dto.getStreet() != null) builder.setStreet(dto.getStreet());
        if (dto.getCity() != null) builder.setCity(dto.getCity());
        if (dto.getState() != null) builder.setState(dto.getState());
        if (dto.getZipCode() != null) builder.setZipCode(dto.getZipCode());
        if (dto.getCountry() != null) builder.setCountry(dto.getCountry());

        return builder.build();
    }

    private CreateCustomerRequest.AddressRequest toAddressRequest(Address address) {
        if (address == null || address.equals(Address.getDefaultInstance())) {
            return null;
        }

        return CreateCustomerRequest.AddressRequest.builder()
                .street(address.getStreet().isEmpty() ? null : address.getStreet())
                .city(address.getCity().isEmpty() ? null : address.getCity())
                .state(address.getState().isEmpty() ? null : address.getState())
                .zipCode(address.getZipCode().isEmpty() ? null : address.getZipCode())
                .country(address.getCountry().isEmpty() ? null : address.getCountry())
                .build();
    }
}
