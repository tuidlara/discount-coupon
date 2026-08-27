package com.arthur.coupon_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CouponUpdateRequest(
        @NotNull
        @Positive
        Double discount,

        @NotNull
        Boolean isActive,

        @NotNull
        @Positive
        BigDecimal minimumAmount
) {
}
