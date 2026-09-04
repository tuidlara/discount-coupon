package com.arthur.coupon_api.controller;

import com.arthur.coupon_api.dto.*;
import com.arthur.coupon_api.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Cria um novo cupom",
            description = "Cria um cupom com código, desconto, valor mínimo, limite de usos e data de expiração."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cupom criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Já existe um cupom com esse código")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CouponResponse createCoupon(@Valid @RequestBody CouponRequest request) {
        return couponService.criarCupom(request);
    }

    @Operation(
            summary = "Lista todos os cupons",
            description = "Retorna uma lista paginada de todos os cupons cadastrados."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cupons listados com sucesso")
    })
    @GetMapping
    public Page<CouponResponse> listAllCoupons(Pageable pageable) {
        return couponService.listarCupons(pageable);
    }

    @Operation(
            summary = "Busca um cupom pelo código",
            description = "Retorna os dados de um cupom específico através do seu código."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cupom encontrado"),
            @ApiResponse(responseCode = "404", description = "Cupom não encontrado")
    })
    @GetMapping("/{code}")
    public CouponResponse getCouponByCode(@PathVariable String code) {
        return couponService.buscarCupomPorCodigo(code);
    }

    @Operation(
            summary = "Exclui um cupom",
            description = "Remove um cupom existente através do seu código."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cupom excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cupom não encontrado")
    })
    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCoupon(@PathVariable String code) {
        couponService.deletarCupom(code);
    }

    @Operation(
            summary = "Atualiza um cupom",
            description = "Atualiza os dados permitidos de um cupom existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cupom atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Cupom não encontrado")
    })
    @PutMapping("/{code}")
    public CouponResponse updateCoupon(@PathVariable String code, @Valid @RequestBody CouponUpdateRequest request) {
        return couponService.atualizarCupom(code, request);
    }

    @Operation(
            summary = "Aplica um cupom",
            description = "Aplica um cupom a um valor informado, respeitando validade, status, valor mínimo e limite de usos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cupom aplicado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Cupom expirado, valor abaixo do mínimo ou dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Cupom não encontrado"),
            @ApiResponse(responseCode = "409", description = "Cupom inativo ou limite de usos atingido")
    })
    @PostMapping("/apply")
    public CouponApplyResponse applyCoupon(@Valid @RequestBody CouponApplyRequest request) {
        return couponService.aplicarCupom(request);
    }
}
