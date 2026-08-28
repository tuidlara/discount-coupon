package com.arthur.coupon_api.service;

import com.arthur.coupon_api.dto.CouponApplyRequest;
import com.arthur.coupon_api.dto.CouponApplyResponse;
import com.arthur.coupon_api.entity.Coupon;
import com.arthur.coupon_api.exception.CouponExpiredException;
import com.arthur.coupon_api.exception.CouponInactiveException;
import com.arthur.coupon_api.exception.CouponMinimumAmountException;
import com.arthur.coupon_api.exception.CouponUsageLimitException;
import com.arthur.coupon_api.repository.CouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CouponServiceTest {

    @Mock
    private CouponRepository couponRepository;

    private CouponService couponService;

    @BeforeEach
    void configurar() {
        couponService = new CouponService(couponRepository);
    }

    @Test
    void deveAplicarCupomComSucesso() {

        //cupom para cenário
        Coupon coupon = new Coupon(
                "DEV20",
                20.0,
                new BigDecimal("100"),
                5
        );

        //garantir validade
        coupon.setExpirationDate(LocalDateTime.now().plusDays(30));

        //programando comportamento do mock
        when(couponRepository.findByCode("DEV20"))
                .thenReturn(Optional.of(coupon));

        //simula requisição de aplicação do cupom
        CouponApplyRequest request = new CouponApplyRequest(
                "DEV20",
                new BigDecimal("200")
        );

        //executa metodo
        CouponApplyResponse resposta = couponService.aplicarCupom(request);

        //verifica o resultado
        assertEquals(0, new BigDecimal("160.00").compareTo(resposta.finalAmount()));

        //verifica se foi incrementado o uso do cupom
        assertEquals(1, coupon.getCurrentUses());
    }

    @Test
    void naoDeveAplicarCupomQuandoAtingirLimiteDeUso() {

        Coupon coupon = new Coupon(
                "DEV20",
                20.0,
                new BigDecimal("100"),
                5
        );

        coupon.setCurrentUses(5);
        coupon.setExpirationDate(LocalDateTime.now().plusDays(30));

        when(couponRepository.findByCode("DEV20"))
                .thenReturn(Optional.of(coupon));

        CouponApplyRequest request = new CouponApplyRequest(
                "DEV20",
                new BigDecimal("200")
        );

        //verifica se a exceção é lançada
        assertThrows(
                CouponUsageLimitException.class,
                () -> couponService.aplicarCupom(request)
        );
    }

    @Test
    void naoDeveAplicarCupomQuandoEstiverInativo() {

        Coupon coupon = new Coupon(
                "DEV20",
                20.0,
                new BigDecimal("100"),
                5);

        coupon.setActive(false);
        coupon.setExpirationDate(LocalDateTime.now().plusDays(30));

        when(couponRepository.findByCode("DEV20"))
                .thenReturn(Optional.of(coupon));

        CouponApplyRequest request = new CouponApplyRequest(
                "DEV20",
                new BigDecimal("200"));

        assertThrows(CouponInactiveException.class,
                () -> couponService.aplicarCupom(request));

    }

    @Test
    void naoDeveAplicarCupomQuandoEstiverExpirado() {

        Coupon coupon = new Coupon(
                "DEV20",
                20.0,
                new BigDecimal("100"),
                5);

        coupon.setExpirationDate(LocalDateTime.now().minusDays(1));

        when(couponRepository.findByCode("DEV20"))
                .thenReturn(Optional.of(coupon));

        CouponApplyRequest request = new CouponApplyRequest(
                "DEV20",
                new BigDecimal("100"));

        assertThrows(CouponExpiredException.class,
                () -> couponService.aplicarCupom(request));

    }

    @Test
    void naoDeveAplicarCupomQuandoNaoAtingirValorMinimo() {
        Coupon coupon = new Coupon(
                "DEV20",
                20.0,
                new BigDecimal("100"),
                5);

        coupon.setMinimumAmount(BigDecimal.valueOf(200));
        coupon.setExpirationDate(LocalDateTime.now().plusDays(30));

        when(couponRepository.findByCode("DEV20"))
                .thenReturn(Optional.of(coupon));

        CouponApplyRequest request = new CouponApplyRequest(
                "DEV20",
                new BigDecimal("100"));

        assertThrows(CouponMinimumAmountException.class,
                () -> couponService.aplicarCupom(request));
    }

}