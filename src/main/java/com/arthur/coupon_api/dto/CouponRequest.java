package com.arthur.coupon_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CouponRequest(

        @Schema(
                description = "Código único do cupom",
                example = "DESCONTO10"
        )
        @NotBlank
        String code,

        @Schema(
                description = "Percentual de desconto do cupom",
                example = "10.0",
                minimum = "0.01",
                maximum = "100"
        )
        @NotNull
        @Positive
        @Max(100)
        Double discount,

        @Schema(
                description = "Valor mínimo da compra para utilizar o cupom",
                example = "50.00"
        )
        @Positive
        BigDecimal minimumAmount,

        @Schema(
                description = "Quantidade máxima de vezes que o cupom pode ser utilizado",
                example = "100"
        )
        @NotNull
        @Positive
        Integer maximumUses
) {
}
