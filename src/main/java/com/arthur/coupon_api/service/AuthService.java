package com.arthur.coupon_api.service;

import com.arthur.coupon_api.dto.CadastroRequest;
import com.arthur.coupon_api.entity.Role;
import com.arthur.coupon_api.entity.User;
import com.arthur.coupon_api.exception.UserAlreadyExistsException;
import com.arthur.coupon_api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void cadastrar(CadastroRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new UserAlreadyExistsException("Email já cadastrado.");
        }
        String senhaCriptografada = passwordEncoder.encode(request.senha());

        User user = new User(request.email(),
                senhaCriptografada);

        user.setRole(Role.CLIENT);

        userRepository.save(user);
    }
}