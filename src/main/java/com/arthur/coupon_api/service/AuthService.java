package com.arthur.coupon_api.service;

import com.arthur.coupon_api.dto.CadastroRequest;
import com.arthur.coupon_api.exception.UserAlreadyExistsException;
import com.arthur.coupon_api.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void cadastrar(CadastroRequest request) {
        if(userRepository.findByEmail(request.email()).isPresent()) {
            throw new UserAlreadyExistsException("Email já cadastrado.");
        }
    }
}