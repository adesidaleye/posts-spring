package com.adesidaleye.blog.services;

import com.adesidaleye.blog.domain.dtos.RegisterRequest;
import com.adesidaleye.blog.domain.entities.User;

import java.util.UUID;

public interface UserService {

    User registerUser(RegisterRequest request);

    User getUserById(UUID id);
}
