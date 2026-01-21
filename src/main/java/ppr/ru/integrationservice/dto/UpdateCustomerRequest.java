package ppr.ru.integrationservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Запрос на обновление клиента")
public class UpdateCustomerRequest {

    @Size(max = 255, message = "Имя не должно превышать 255 символов")
    @Schema(description = "Имя клиента", example = "Иван Петров")
    private String name;

    @Email(message = "Некорректный формат email")
    @Schema(description = "Email клиента", example = "ivan.petrov@email.com")
    private String email;

    @Schema(description = "Телефон клиента", example = "+7 (999) 123-45-67")
    private String phone;

    @Valid
    @Schema(description = "Адрес клиента")
    private CreateCustomerRequest.AddressRequest address;
}
