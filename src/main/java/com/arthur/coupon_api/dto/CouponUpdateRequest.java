package com.arthur.coupon_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CouponUpdateRequest(

        @Schema(
                description = "Novo percentual de desconto do cupom",
                example = "15.0",
                minimum = "0.01",
                maximum = "100"
        )
        @NotNull
        @Max(100)
        @Positive
        Double discount,

        @Schema(
                description = "Define se o cupom está ativo",
                example = "true"
        )
        @NotNull
        Boolean isActive,

        @Schema(
                description = "Novo valor mínimo da compra para utilizar o cupom",
                example = "100.00",
                minimum = "0.01"
        )
        @NotNull
        @Positive
        BigDecimal minimumAmount
) {
}