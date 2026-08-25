package com.arthur.coupon_api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CouponRequest(

    @NotBlank
    String code,

    @NotNull
    @Positive
    @Max(100)
    Double discount
) {
}
