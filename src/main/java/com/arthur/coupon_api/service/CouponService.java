package com.arthur.coupon_api.service;

import com.arthur.coupon_api.dto.*;
import com.arthur.coupon_api.entity.Coupon;
import com.arthur.coupon_api.exception.CouponNotFoundException;
import com.arthur.coupon_api.repository.CouponAlreadyExistsException;
import com.arthur.coupon_api.repository.CouponRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
        if (couponRepository.existsByCode(coupon.getCode())) {
            throw new CouponAlreadyExistsException("Cupom já existe");
        }
        Coupon cupomSalvo = couponRepository.save(coupon);
        return toResponse(cupomSalvo);
    }

    public Page<CouponResponse> listarCupons(Pageable pageable) {
        Page<Coupon> cupons = couponRepository.findAll(pageable);
        return cupons.map(this::toResponse);
    }

    public CouponResponse buscarCupomPorCodigo(String code) {
        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new CouponNotFoundException("Cupom não encontrado"));
        return toResponse(coupon);
    }

    public void deletarCupom(String code) {
        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new CouponNotFoundException("Cupom não encontrado"));
        couponRepository.delete(coupon);
    }

    public CouponResponse atualizarCupom(String code, CouponUpdateRequest request) {
        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new CouponNotFoundException("Cupom não encontrado"));

        coupon.setDiscount(request.discount());
        couponRepository.save(coupon);
        return toResponse(coupon);
    }

    public CouponApplyResponse aplicarCupom(CouponApplyRequest request) {
        Coupon coupon = couponRepository.findByCode(request.code())
                .orElseThrow(() -> new CouponNotFoundException("Cupom não encontrado"));

        BigDecimal discount = BigDecimal.valueOf(coupon.getDiscount());
        BigDecimal valorDesconto = request.amount().multiply(discount)
                .divide(BigDecimal.valueOf(100));

        BigDecimal valorFinal = request.amount().subtract(valorDesconto);
        return new CouponApplyResponse(
                coupon.getCode(),
                request.amount(),
                valorFinal,
                coupon.getDiscount()
        );

    }

}
