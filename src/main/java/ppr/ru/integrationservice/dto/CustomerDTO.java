package ppr.ru.integrationservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Информация о клиенте")
public class CustomerDTO {

    @Schema(description = "Уникальный идентификатор клиента", example = "507f1f77bcf86cd799439011")
    private String id;

    @Schema(description = "Имя клиента", example = "Иван Петров")
    private String name;

    @Schema(description = "Email клиента", example = "ivan.petrov@email.com")
    private String email;

    @Schema(description = "Телефон клиента", example = "+7 (999) 123-45-67")
    private String phone;

    @Schema(description = "Адрес клиента")
    private AddressDTO address;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "Адрес")
    public static class AddressDTO {

        @Schema(description = "Улица", example = "ул. Ленина, д. 10, кв. 5")
        private String street;

        @Schema(description = "Город", example = "Москва")
        private String city;

        @Schema(description = "Область/регион", example = "Московская область")
        private String state;

        @Schema(description = "Почтовый индекс", example = "123456")
        private String zipCode;

        @Schema(description = "Страна", example = "Россия")
        private String country;
    }
}
