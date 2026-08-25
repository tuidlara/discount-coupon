package com.arthur.coupon_api.dto;

import java.time.LocalDateTime;

public record CouponResponse(
    Long id,
    String code,
    Double discount,
    LocalDateTime createdAt,
    LocalDateTime expirationDate
) {
}
