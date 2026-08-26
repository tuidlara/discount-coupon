package com.arthur.coupon_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CouponResponse(
    Long id,
    String code,
    Double discount,
    BigDecimal minimumAmount,
    LocalDateTime createdAt,
    LocalDateTime expirationDate
) {
}
