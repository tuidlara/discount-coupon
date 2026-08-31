package com.arthur.coupon_api.repository;

import com.arthur.coupon_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long > {

    Optional<User> findByEmail(String email);
}
