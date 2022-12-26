package com.mateuszczyz.Todo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequestDto {
    @Email(message = "{validation.email.invalid-format}")
    @NotBlank(message = "{validation.email.not-blank}")
    private String email;
    @NotBlank(message = "{validation.password.not-blank}")
    private String password;
}
