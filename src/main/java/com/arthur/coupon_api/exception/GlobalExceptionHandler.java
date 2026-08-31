package com.arthur.coupon_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> tratarErroValidacao(MethodArgumentNotValidException e) {

        Map<String, String> erros = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(erroCampo -> {
            erros.put(erroCampo.getField(), erroCampo.getDefaultMessage());
        });

        return erros;
    }

    @ExceptionHandler(CouponNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String tratarErroCupomNaoEncontrado(CouponNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(CouponAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String tratarErroCupomJaExiste(CouponAlreadyExistsException e) {
        return e.getMessage();

    }

    @ExceptionHandler(CouponExpiredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String tratarErroCupomExpirado(CouponExpiredException e) {
        return e.getMessage();
    }

    @ExceptionHandler(CouponMinimumAmountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String tratarErroCupomValorMinimo(CouponMinimumAmountException e) {
        return e.getMessage();
    }

    @ExceptionHandler(CouponUsageLimitException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String tratarErroLimiteDeUsoCupom(CouponUsageLimitException e) {
        return e.getMessage();
    }

    @ExceptionHandler(CouponInactiveException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String tratarErroCupomInativo(CouponInactiveException e) {
        return e.getMessage();
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String tratarErroUsuarioJaExiste(UserAlreadyExistsException e) {
        return e.getMessage();
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String tratarCredenciaisInvalidas(InvalidCredentialsException e) {
        return e.getMessage();
    }
}
