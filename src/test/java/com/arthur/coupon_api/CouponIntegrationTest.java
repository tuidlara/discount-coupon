package com.arthur.coupon_api;

import com.arthur.coupon_api.entity.Role;
import com.arthur.coupon_api.entity.User;
import com.arthur.coupon_api.repository.CouponRepository;
import com.arthur.coupon_api.repository.UserRepository;
import com.arthur.coupon_api.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CouponIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private TokenService tokenService;

    @Test
    void deveCriarCupomQuandoAdminEstiverAutenticado() throws Exception {

        User admin = new User("admin@teste.com", "senha");
        admin.setRole(Role.ADMIN);

        userRepository.saveAndFlush(admin);

        String token = tokenService.gerarToken(admin);

        mockMvc.perform(
                        post("/coupons")
                                .header("Authorization", "Bearer " + token)
                                .content("""
                                        {
                                            "code": "INTEGRACAO20",
                                            "discount": 20.0,
                                            "minimumAmount": 100,
                                            "maximumUses": 5
                                        }
                                        """)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("INTEGRACAO20"))
                .andExpect(jsonPath("$.discount").value(20.0))
                .andExpect(jsonPath("$.minimumAmount").value(100))
                .andExpect(jsonPath("$.maximumUses").value(5))
                .andExpect(jsonPath("$.currentUses").value(0))
                .andExpect(jsonPath("$.isActive").value(true));

        //verifica se realmente foi salvo no banco de dados
        assertTrue(couponRepository.findByCode("INTEGRACAO20").isPresent());
    }
}
