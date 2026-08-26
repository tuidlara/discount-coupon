package com.arthur.coupon_api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CouponRequest(

    @NotBlank
    String code,

    @NotNull
    @Positive
    @Max(100)
    Double discount,

    @NotNull
    @Positive
    BigDecimal minimumAmount,

    @NotNull
    @Positive
    Integer maximumUses
) {
}
