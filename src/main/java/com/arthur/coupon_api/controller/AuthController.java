package com.arthur.coupon_api.controller;

import com.arthur.coupon_api.dto.CadastroRequest;
import com.arthur.coupon_api.dto.LoginRequest;
import com.arthur.coupon_api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            summary = "Cadastra um novo usuário",
            description = "Cria um usuário com perfil CLIENT e senha criptografada."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Email já cadastrado")
    })

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String registrar(@Valid @RequestBody CadastroRequest request) {
        authService.cadastrar(request);
        return "Usuário cadastrado com sucesso!";
    }

    @Operation(
            summary = "Realiza login",
            description = "Autentica o usuário e retorna um token JWT."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Email ou senha inválidos")
    })
    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
