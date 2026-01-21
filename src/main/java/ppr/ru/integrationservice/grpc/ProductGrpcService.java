package ppr.ru.integrationservice.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ppr.ru.integrationservice.dto.ProductDTO;
import ppr.ru.integrationservice.grpc.generated.*;
import ppr.ru.integrationservice.service.ProductService;

import java.util.List;
import java.util.Optional;

@GrpcService
@RequiredArgsConstructor
public class ProductGrpcService extends ProductServiceGrpc.ProductServiceImplBase {

    private final ProductService productService;

    @Override
    public void getProducts(GetProductsRequest request, StreamObserver<GetProductsResponse> responseObserver) {
        List<ProductDTO> products = request.getIncludeAvailability()
                ? productService.getAllProductsWithAvailability()
                : productService.getAllProducts();

        GetProductsResponse response = GetProductsResponse.newBuilder()
                .addAllProducts(products.stream().map(this::toProto).toList())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getProductById(GetProductByIdRequest request, StreamObserver<ProductResponse> responseObserver) {
        Optional<ProductDTO> product = request.getIncludeAvailability()
                ? productService.getProductByIdWithAvailability(request.getId())
                : productService.getProductById(request.getId());

        ProductResponse.Builder builder = ProductResponse.newBuilder()
                .setFound(product.isPresent());

        product.ifPresent(p -> builder.setProduct(toProto(p)));

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getProductBySku(GetProductBySkuRequest request, StreamObserver<ProductResponse> responseObserver) {
        Optional<ProductDTO> product = request.getIncludeAvailability()
                ? productService.getProductBySkuWithAvailability(request.getSku())
                : productService.getProductBySku(request.getSku());

        ProductResponse.Builder builder = ProductResponse.newBuilder()
                .setFound(product.isPresent());

        product.ifPresent(p -> builder.setProduct(toProto(p)));

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getProductsByCategory(GetProductsByCategoryRequest request, StreamObserver<GetProductsResponse> responseObserver) {
        List<ProductDTO> products = request.getIncludeAvailability()
                ? productService.getProductsByCategoryWithAvailability(request.getCategory())
                : productService.getProductsByCategory(request.getCategory());

        GetProductsResponse response = GetProductsResponse.newBuilder()
                .addAllProducts(products.stream().map(this::toProto).toList())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private Product toProto(ProductDTO dto) {
        Product.Builder builder = Product.newBuilder()
                .setId(dto.getId())
                .setName(dto.getName())
                .setPrice(dto.getPrice().doubleValue())
                .setSku(dto.getSku());

        if (dto.getDescription() != null) {
            builder.setDescription(dto.getDescription());
        }
        if (dto.getCategory() != null) {
            builder.setCategory(dto.getCategory());
        }
        if (dto.getAvailabilities() != null) {
            builder.addAllAvailabilities(
                    dto.getAvailabilities().stream()
                            .map(this::toProto)
                            .toList()
            );
        }

        return builder.build();
    }

    private Availability toProto(ProductDTO.AvailabilityDTO dto) {
        Availability.Builder builder = Availability.newBuilder()
                .setId(dto.getId())
                .setQuantity(dto.getQuantity())
                .setWarehouse(dto.getWarehouse());

        if (dto.getLastUpdated() != null) {
            builder.setLastUpdated(dto.getLastUpdated().toString());
        }

        return builder.build();
    }
}
