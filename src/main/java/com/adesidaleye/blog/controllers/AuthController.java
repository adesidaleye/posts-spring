package com.adesidaleye.blog.controllers;

import com.adesidaleye.blog.domain.dtos.AuthResponse;
import com.adesidaleye.blog.domain.dtos.LoginRequest;
import com.adesidaleye.blog.domain.dtos.RegisterRequest;
import com.adesidaleye.blog.mappers.UserMapper;
import com.adesidaleye.blog.services.AuthenticationService;
import com.adesidaleye.blog.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        userService.register(request);

        // auto-login after signup
        UserDetails userDetails = authenticationService.authenticate(
                request.getEmail(),
                request.getPassword()
        );
        String tokenValue = authenticationService.generateToken(userDetails);

        AuthResponse response = AuthResponse.builder()
                .token(tokenValue)
                .expiresIn(Duration.ofHours(24).toSeconds())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest loginRequest
    ) {
        UserDetails userDetails = authenticationService.authenticate(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );
        String tokenValue = authenticationService.generateToken(userDetails);

        AuthResponse response = AuthResponse.builder()
                .token(tokenValue)
                .expiresIn(Duration.ofHours(24).toSeconds())
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
