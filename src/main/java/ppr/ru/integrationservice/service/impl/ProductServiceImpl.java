package ppr.ru.integrationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ppr.ru.integrationservice.dto.ProductDTO;
import ppr.ru.integrationservice.dto.ProductMapper;
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
}
