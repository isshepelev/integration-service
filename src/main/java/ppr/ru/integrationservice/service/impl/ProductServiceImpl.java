package ppr.ru.integrationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ppr.ru.integrationservice.dto.CreateProductRequest;
import ppr.ru.integrationservice.dto.ProductDTO;
import ppr.ru.integrationservice.dto.ProductMapper;
import ppr.ru.integrationservice.dto.UpdateProductRequest;
import ppr.ru.integrationservice.model.jpa.Product;
import ppr.ru.integrationservice.repository.jpa.ProductRepository;
import ppr.ru.integrationservice.service.ProductService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Override
    public List<ProductDTO> getAllProductsWithAvailability() {
        return productRepository.findAllWithAvailability().stream()
                .map(productMapper::toDTOWithAvailability)
                .toList();
    }

    @Override
    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDTO);
    }

    @Override
    public Optional<ProductDTO> getProductByIdWithAvailability(Long id) {
        return productRepository.findByIdWithAvailability(id)
                .map(productMapper::toDTOWithAvailability);
    }

    @Override
    public Optional<ProductDTO> getProductBySku(String sku) {
        return productRepository.findBySku(sku)
                .map(productMapper::toDTO);
    }

    @Override
    public Optional<ProductDTO> getProductBySkuWithAvailability(String sku) {
        return productRepository.findBySkuWithAvailability(sku)
                .map(productMapper::toDTOWithAvailability);
    }

    @Override
    public List<ProductDTO> getProductsByCategory(String category) {
        return productRepository.findByCategory(category).stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Override
    public List<ProductDTO> getProductsByCategoryWithAvailability(String category) {
        return productRepository.findByCategoryWithAvailability(category).stream()
                .map(productMapper::toDTOWithAvailability)
                .toList();
    }

    @Override
    @Transactional
    public ProductDTO createProduct(CreateProductRequest request) {
        Product product = productMapper.toEntity(request);
        Product saved = productRepository.save(product);
        return productMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public Optional<ProductDTO> updateProduct(Long id, UpdateProductRequest request) {
        return productRepository.findById(id)
                .map(product -> {
                    if (request.getName() != null) product.setName(request.getName());
                    if (request.getDescription() != null) product.setDescription(request.getDescription());
                    if (request.getPrice() != null) product.setPrice(request.getPrice());
                    if (request.getSku() != null) product.setSku(request.getSku());
                    if (request.getCategory() != null) product.setCategory(request.getCategory());
                    return productMapper.toDTO(productRepository.save(product));
                });
    }

    @Override
    @Transactional
    public Optional<ProductDTO> partialUpdateProduct(Long id, UpdateProductRequest request) {
        return productRepository.findById(id)
                .map(product -> {
                    productMapper.updateEntity(request, product);
                    return productMapper.toDTO(productRepository.save(product));
                });
    }

    @Override
    @Transactional
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
