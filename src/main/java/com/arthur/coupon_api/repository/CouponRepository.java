package com.arthur.coupon_api.repository;

import com.arthur.coupon_api.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
