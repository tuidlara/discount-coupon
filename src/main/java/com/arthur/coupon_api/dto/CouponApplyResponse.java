package com.arthur.coupon_api.dto;

import java.math.BigDecimal;

public record CouponApplyResponse(
    String code,
    BigDecimal originalAmount,
    BigDecimal finalAmount,
    Double discount
) {
}
