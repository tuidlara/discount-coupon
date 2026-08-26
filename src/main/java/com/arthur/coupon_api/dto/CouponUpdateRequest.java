package com.arthur.coupon_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CouponUpdateRequest(
        @NotNull
        @Positive
        Double discount,

        @NotNull
        Boolean isActive
) {
}
