package ppr.ru.integrationservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Запрос на создание клиента")
public class CreateCustomerRequest {

    @NotBlank(message = "Имя клиента обязательно")
    @Size(max = 255, message = "Имя не должно превышать 255 символов")
    @Schema(description = "Имя клиента", example = "Иван Петров", required = true)
    private String name;

    @NotBlank(message = "Email обязателен")
    @Email(message = "Некорректный формат email")
    @Schema(description = "Email клиента", example = "ivan.petrov@email.com", required = true)
    private String email;

    @Schema(description = "Телефон клиента", example = "+7 (999) 123-45-67")
    private String phone;

    @Valid
    @Schema(description = "Адрес клиента")
    private AddressRequest address;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "Адрес")
    public static class AddressRequest {

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
