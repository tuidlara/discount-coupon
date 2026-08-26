package com.arthur.coupon_api.exception;

public class CouponMinimumAmountException extends RuntimeException {
    public CouponMinimumAmountException(String message) {
        super(message);
    }
}
