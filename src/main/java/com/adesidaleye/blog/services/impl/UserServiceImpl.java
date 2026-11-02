package com.adesidaleye.blog.services.impl;

import com.adesidaleye.blog.domain.dtos.RegisterRequest;
import com.adesidaleye.blog.domain.entities.User;
import com.adesidaleye.blog.exceptions.EmailAlreadyExistsException;
import com.adesidaleye.blog.repositories.UserRepository;
import com.adesidaleye.blog.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build();

        return userRepository.save(user);
    }
}