package com.arthur.coupon_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @Schema(
                description = "Email utilizado para realizar o login",
                example = "usuario@email.com"
        )
        @Email(message = "Email inválido.")
        @NotBlank(message = "Email não pode ser vazio.")
        String email,

        @Schema(
                description = "Senha do usuário",
                example = "senha123"
        )
        @NotBlank(message = "Senha não pode ser vazia.")
        String senha
) {
}
