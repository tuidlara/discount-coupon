package com.arthur.coupon_api.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;
    private Double discount;

    private LocalDateTime createdAt;
    private LocalDateTime expirationDate;

    @PrePersist
    public void aoCriar() {
        this.createdAt = LocalDateTime.now();
        this.expirationDate = this.createdAt.plusDays(60);
    }

    public Coupon() {
    }

    public Coupon(String code, Double discount) {
        this.code = code;
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }
}
