package com.arthur.coupon_api.dto;

public record CouponRequest(
    String code,
    Double discount
) {
}
