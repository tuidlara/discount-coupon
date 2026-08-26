package com.arthur.coupon_api.exception;

public class CouponInactiveException extends RuntimeException {
    public CouponInactiveException(String message) {
        super(message);
    }
}
