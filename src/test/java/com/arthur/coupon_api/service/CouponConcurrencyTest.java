package com.arthur.coupon_api.service;

import com.arthur.coupon_api.dto.CouponApplyRequest;
import com.arthur.coupon_api.dto.CouponApplyResponse;
import com.arthur.coupon_api.entity.Coupon;
import com.arthur.coupon_api.exception.CouponUsageLimitException;
import com.arthur.coupon_api.repository.CouponRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CouponConcurrencyTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    void devePermitirApenasUmUsoEmConcorrencia() throws Exception {

        String code = "CONCORRENCIA-" + UUID.randomUUID();

        Coupon coupon = new Coupon(
                code,
                20.0,
                new BigDecimal("10"),
                1
        );

        couponRepository.save(coupon);

        CouponApplyRequest request = new CouponApplyRequest(
                code,
                new BigDecimal("100")
        );

        //executor com 2 threads disponíveis
        ExecutorService executor = Executors.newFixedThreadPool(2);

        //barreira
        CountDownLatch inicio = new CountDownLatch(1);

        Future<CouponApplyResponse> primeiraTentativa = executor.submit(() -> {
            inicio.await();
            return couponService.aplicarCupom(request);
        });

        //tarefa ainda ta acontecendo então o resultado fica guardado no Future
        Future<CouponApplyResponse> segundaTentativa = executor.submit(() -> {
            inicio.await();
            return couponService.aplicarCupom(request);
        });

        //threads saem do await
        inicio.countDown();

        int sucessos = 0;
        int erros = 0;

        try {
            primeiraTentativa.get();
            sucessos++;
        } catch (Exception e) {
            if (e.getCause() instanceof CouponUsageLimitException) {
                erros++;
            } else {
                throw e;
            }
        }

        try {
            segundaTentativa.get();
            sucessos++;
        } catch (Exception e) {
            if (e.getCause() instanceof CouponUsageLimitException) {
                erros++;
            } else {
                throw e;
            }
        }

        executor.shutdown();

        Coupon couponAtualizado = couponRepository
                .findById(coupon.getId())
                .orElseThrow();

        assertEquals(1, sucessos);
        assertEquals(1, erros);
        assertEquals(1, couponAtualizado.getCurrentUses());
    }
}
