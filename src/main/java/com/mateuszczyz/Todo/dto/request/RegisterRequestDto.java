package com.mateuszczyz.Todo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequestDto {
    @Email(message = "{validation.email.invalid-format}")
    @NotBlank(message = "{validation.email.not-blank}")
    private String email;
    @NotBlank(message = "{register.password.not-blank}")
    private String password;
}
