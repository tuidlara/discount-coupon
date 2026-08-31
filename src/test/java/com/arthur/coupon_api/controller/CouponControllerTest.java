package com.arthur.coupon_api.controller;

import com.arthur.coupon_api.dto.CouponRequest;
import com.arthur.coupon_api.dto.CouponResponse;
import com.arthur.coupon_api.service.CouponService;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//testar a camada web
@WebMvcTest(CouponController.class)
public class CouponControllerTest {

    // cria um mock do service
    @MockitoBean
    private CouponService couponService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveCriarCupom() throws Exception {

        // dados que seriam recebidos pelo controller no post
        CouponRequest request = new CouponRequest(
                "DEV20",
                20.0,
                new BigDecimal("100"),
                5);

        // resposta que finge que o service retornou
        CouponResponse response = new CouponResponse(
                1L,
                "DEV20",
                20.0,
                new BigDecimal("100"),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(60),
                5,
                0,
                true);

        when(couponService.criarCupom(request))
                .thenReturn(response);

        //simula uma requisição http post real para /coupons
        mockMvc.perform(
                        post("/coupons")

                                // corpo da requisição
                                .content("""
                                    {
                                        "code": "DEV20",
                                        "discount": 20.0,
                                        "minimumAmount": 100,
                                        "maximumUses": 5
                                    }
                                    """)

                                //avisar que é um json
                                .contentType(MediaType.APPLICATION_JSON))

                // verifica o status http retornado pelo controller
                .andExpect(status().isCreated())

                // verifica os dados que vieram no json da resposta
                .andExpect(jsonPath("$.code").value("DEV20"))
                .andExpect(jsonPath("$.discount").value(20.0))
                .andExpect(jsonPath("$.minimumAmount").value(100))
                .andExpect(jsonPath("$.maximumUses").value(5))
                .andExpect(jsonPath("$.currentUses").value(0))
                .andExpect(jsonPath("$.isActive").value(true));
    }

    @Test
    void naoDeveCriarCupomComDescontoInvalido() throws Exception {

        mockMvc.perform(
                post("/coupons")
                        .content("""
                        {
                            "code": "DEV20",
                            "discount": 150,
                            "minimumAmount": 100,
                            "maximumUses": 5
                        }
                        """)
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isBadRequest());
    }

}
