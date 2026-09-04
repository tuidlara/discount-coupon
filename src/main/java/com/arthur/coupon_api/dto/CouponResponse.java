package com.arthur.coupon_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CouponResponse(

        @Schema(
                description = "Identificador único do cupom",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Código do cupom",
                example = "DESCONTO10"
        )
        String code,

        @Schema(
                description = "Percentual de desconto aplicado pelo cupom",
                example = "10.0"
        )
        Double discount,

        @Schema(
                description = "Valor mínimo da compra para utilizar o cupom",
                example = "50.00"
        )
        BigDecimal minimumAmount,

        @Schema(
                description = "Data e hora de criação do cupom",
                example = "2026-09-02T10:30:00"
        )
        LocalDateTime createdAt,

        @Schema(
                description = "Data e hora de expiração do cupom",
                example = "2026-11-01T10:30:00"
        )
        LocalDateTime expirationDate,

        @Schema(
                description = "Quantidade máxima de utilizações do cupom",
                example = "100"
        )
        Integer maximumUses,

        @Schema(
                description = "Quantidade de vezes que o cupom já foi utilizado",
                example = "15"
        )
        Integer currentUses,

        @Schema(
                description = "Indica se o cupom está ativo",
                example = "true"
        )
        boolean isActive
) {
}