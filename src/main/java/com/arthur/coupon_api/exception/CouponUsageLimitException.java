package com.arthur.coupon_api.exception;

public class CouponUsageLimitException extends RuntimeException {
    public CouponUsageLimitException(String message) {
        super(message);
    }
}
