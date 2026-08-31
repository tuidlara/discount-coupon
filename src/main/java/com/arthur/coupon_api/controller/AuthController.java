package com.arthur.coupon_api.controller;

import com.arthur.coupon_api.dto.CadastroRequest;
import com.arthur.coupon_api.dto.LoginRequest;
import com.arthur.coupon_api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String registrar(@Valid @RequestBody CadastroRequest request) {
        authService.cadastrar(request);
        return "Usuário cadastrado com sucesso!";
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
