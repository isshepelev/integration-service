package ppr.ru.integrationservice.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ppr.ru.integrationservice.model.jpa.Product;
import ppr.ru.integrationservice.model.jpa.ProductAvailability;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "availabilities", ignore = true)
    ProductDTO toDTO(Product product);

    @Mapping(target = "availabilities", source = "availabilities", qualifiedByName = "toAvailabilityDTOList")
    ProductDTO toDTOWithAvailability(Product product);

    @Named("toAvailabilityDTOList")
    default List<ProductDTO.AvailabilityDTO> toAvailabilityDTOList(List<ProductAvailability> availabilities) {
        if (availabilities == null) {
            return Collections.emptyList();
        }
        return availabilities.stream()
                .map(this::toAvailabilityDTO)
                .toList();
    }

    ProductDTO.AvailabilityDTO toAvailabilityDTO(ProductAvailability availability);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "availabilities", ignore = true)
    Product toEntity(CreateProductRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "availabilities", ignore = true)
    void updateEntity(UpdateProductRequest request, @MappingTarget Product product);
}
