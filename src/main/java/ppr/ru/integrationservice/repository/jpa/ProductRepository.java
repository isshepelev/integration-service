package ppr.ru.integrationservice.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ppr.ru.integrationservice.model.jpa.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);

    List<Product> findByCategory(String category);

    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.availabilities")
    List<Product> findAllWithAvailability();

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.availabilities WHERE p.id = :id")
    Optional<Product> findByIdWithAvailability(Long id);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.availabilities WHERE p.sku = :sku")
    Optional<Product> findBySkuWithAvailability(String sku);

    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.availabilities WHERE p.category = :category")
    List<Product> findByCategoryWithAvailability(String category);
}
