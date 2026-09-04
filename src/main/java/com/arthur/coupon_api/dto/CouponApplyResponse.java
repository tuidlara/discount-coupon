package com.arthur.coupon_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record CouponApplyResponse(
        @Schema(
                description = "Código do cupom aplicado",
                example = "DESCONTO10"
        )
        String code,

        @Schema(
                description = "Valor original da compra antes do desconto",
                example = "150.00"
        )
        BigDecimal originalAmount,

        @Schema(
                description = "Valor final da compra após a aplicação do desconto",
                example = "135.00"
        )
        BigDecimal finalAmount,

        @Schema(
                description = "Percentual de desconto aplicado",
                example = "10.0"
        )
        Double discount
) {
}
