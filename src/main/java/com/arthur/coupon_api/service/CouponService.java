package com.arthur.coupon_api.service;

import com.arthur.coupon_api.dto.CouponRequest;
import com.arthur.coupon_api.dto.CouponResponse;
import com.arthur.coupon_api.entity.Coupon;
import com.arthur.coupon_api.repository.CouponRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    private CouponResponse toResponse(Coupon coupon) {
        return new CouponResponse(
                coupon.getId(),
                coupon.getCode(),
                coupon.getDiscount());
    }

    private Coupon toEntity(CouponRequest request) {
        return new Coupon(
                request.code(),
                request.discount());
    }

    public CouponResponse criarCupom(CouponRequest request) {
        Coupon coupon = toEntity(request);
        Coupon cupomSalvo = couponRepository.save(coupon);
        return toResponse(cupomSalvo);
    }

    public Page<CouponResponse> listarCupons(Pageable pageable) {
        Page<Coupon> cupons = couponRepository.findAll(pageable);
        return cupons.map(this::toResponse);
    }




}
