package com.arthur.coupon_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @Email(message = "Email inválido.")
        @NotBlank(message = "Email não pode ser vazio.")
        String email,

        @NotBlank(message = "Senha não pode ser vazia.")
        String senha
) {
}
