package com.arthur.coupon_api.controller;

import com.arthur.coupon_api.dto.CouponRequest;
import com.arthur.coupon_api.dto.CouponResponse;
import com.arthur.coupon_api.dto.CouponUpdateRequest;
import com.arthur.coupon_api.service.CouponService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CouponResponse createCoupon(@Valid @RequestBody CouponRequest request) {
        return couponService.criarCupom(request);
    }

    @GetMapping
    public Page<CouponResponse> listAllCoupons(Pageable pageable) {
        return couponService.listarCupons(pageable);
    }

    @GetMapping("/{code}")
    public CouponResponse getCouponByCode(@PathVariable String code) {
        return couponService.buscarCupomPorCodigo(code);
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCoupon(@PathVariable String code) {
        couponService.deletarCupom(code);
    }

    @PutMapping("/{code}")
    public CouponResponse updateCoupon(@PathVariable String code, @Valid @RequestBody CouponUpdateRequest request){
        return couponService.atualizarCupom(code, request);
    }
}
