package com.arthur.coupon_api.service;

import com.arthur.coupon_api.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenService {

    private static final long EXPIRATION_TIME = 2 * 60 * 60 * 1000;

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

    }

    public String gerarToken(User user) {

        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole().name())
                .issuedAt(agora)
                .expiration(expiracao)
                .signWith(getKey())
                .compact();

    }
}
