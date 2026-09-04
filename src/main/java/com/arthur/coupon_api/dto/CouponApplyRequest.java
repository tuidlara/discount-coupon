package com.arthur.coupon_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CouponApplyRequest(

        @Schema(
                description = "Código do cupom que será aplicado",
                example = "DESCONTO10"
        )
        @NotBlank
        String code,

        @Schema(
                description = "Valor original da compra",
                example = "150.00",
                minimum = "0.01"
        )
        @NotNull
        @Positive
        BigDecimal amount
) {
}
