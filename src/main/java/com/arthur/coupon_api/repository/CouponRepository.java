package com.arthur.coupon_api.repository;

import com.arthur.coupon_api.entity.Coupon;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;


public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Coupon> findByCode(String code);

    boolean existsByCode(String code);

    Page<Coupon> findByIsActive(boolean isActive, Pageable pageable);

}
