package ppr.ru.integrationservice.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ppr.ru.integrationservice.model.jpa.ProductAvailability;

import java.util.List;

@Repository
public interface ProductAvailabilityRepository extends JpaRepository<ProductAvailability, Long> {

    List<ProductAvailability> findByProductId(Long productId);

    List<ProductAvailability> findByWarehouse(String warehouse);
}
