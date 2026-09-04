package com.arthur.coupon_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastroRequest(

        @Schema(
                description = "Email do usuário",
                example = "usuario@email.com"
        )
        @Email(message = "Email inválido.")
        @NotBlank(message = "Email não pode ser vazio.")
        String email,

        @Schema(
                description = "Senha do usuário, com no mínimo 8 caracteres",
                example = "senha123"
        )
        @NotBlank(message = "Senha não pode ser vazia.")
        @Size(min = 8, message = "Senha deve conter no mínimo 8 caracteres.")
        String senha
) {
}
