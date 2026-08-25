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
}
