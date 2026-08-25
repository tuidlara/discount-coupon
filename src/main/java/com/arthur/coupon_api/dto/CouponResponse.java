package com.arthur.coupon_api.dto;

public record CouponResponse(
    Long id,
    String code,
    Double discount
) {
}
