package com.arthur.coupon_api.config;

import com.arthur.coupon_api.controller.CouponController;
import com.arthur.coupon_api.dto.CouponRequest;
import com.arthur.coupon_api.dto.CouponResponse;
import com.arthur.coupon_api.entity.Role;
import com.arthur.coupon_api.entity.User;
import com.arthur.coupon_api.repository.UserRepository;
import com.arthur.coupon_api.service.CouponService;
import com.arthur.coupon_api.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//mockMvc com os filtros de segurança habilitados
@AutoConfigureMockMvc(addFilters = true)
@WebMvcTest(CouponController.class)

//importa manualmente a configuração de segurança para o teste
@Import(SecurityConfig.class)
public class SecurityConfigTest {

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private CouponService couponService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveNegarAcessoSemToken() throws Exception {
        mockMvc.perform(
                        get("/coupons")
                )
                .andExpect(status().isUnauthorized());

    }

    @Test
    void naoDeveCriarCupomQuandoUsuarioForClient() throws Exception {
        User usuario = new User("cliente@email.com", "senha");
        usuario.setRole(Role.CLIENT);

        when(userRepository.findByEmail("cliente@email.com"))
                .thenReturn(Optional.of(usuario));

        when(tokenService.validarToken("token-client"))
                .thenReturn("cliente@email.com");

        mockMvc.perform(
                        post("/coupons")
                                .header("Authorization", "Bearer token-client")
                )
                .andExpect(status().isForbidden());

    }

    @Test
    void devePermitirCriarCupomQuandoUsuarioForAdmin() throws Exception {
        User usuario = new User("admin@email.com", "senha");
        usuario.setRole(Role.ADMIN);

        CouponRequest request = new CouponRequest(
                "DEV20",
                20.0,
                BigDecimal.valueOf(100),
                5
        );

        when(userRepository.findByEmail("admin@email.com"))
                .thenReturn(Optional.of(usuario));

        when(tokenService.validarToken("token-admin"))
                .thenReturn("admin@email.com");

        CouponResponse response = new CouponResponse(
                1L,
                "DEV20",
                20.0,
                BigDecimal.valueOf(100),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(60),
                5,
                0,
                true
        );

        when(couponService.criarCupom(request))
                .thenReturn(response);

        mockMvc.perform(
                        post("/coupons")
                                .header("Authorization", "Bearer token-admin")
                                .content("""
                                            {
                                                "code": "DEV20",
                                                "discount": 20.0,
                                                "minimumAmount": 100,
                                                "maximumUses": 5
                                            }
                                        """)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());


    }
}
