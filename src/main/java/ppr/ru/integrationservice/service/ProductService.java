package ppr.ru.integrationservice.service;

import ppr.ru.integrationservice.dto.CreateProductRequest;
import ppr.ru.integrationservice.dto.ProductDTO;
import ppr.ru.integrationservice.dto.UpdateProductRequest;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    List<ProductDTO> getAllProductsWithAvailability();

    Optional<ProductDTO> getProductById(Long id);

    Optional<ProductDTO> getProductByIdWithAvailability(Long id);

    Optional<ProductDTO> getProductBySku(String sku);

    Optional<ProductDTO> getProductBySkuWithAvailability(String sku);

    List<ProductDTO> getProductsByCategory(String category);

    List<ProductDTO> getProductsByCategoryWithAvailability(String category);

    ProductDTO createProduct(CreateProductRequest request);

    Optional<ProductDTO> updateProduct(Long id, UpdateProductRequest request);

    Optional<ProductDTO> partialUpdateProduct(Long id, UpdateProductRequest request);

    boolean deleteProduct(Long id);
}
