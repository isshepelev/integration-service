package ppr.ru.integrationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String sku;
    private String category;
    private List<AvailabilityDTO> availabilities;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AvailabilityDTO {
        private Long id;
        private Integer quantity;
        private String warehouse;
        private LocalDateTime lastUpdated;
    }
}
