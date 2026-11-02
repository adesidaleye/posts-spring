package com.adesidaleye.blog.services;

import com.adesidaleye.blog.domain.dtos.RegisterRequest;
import com.adesidaleye.blog.domain.entities.User;

public interface UserService {

    User register(RegisterRequest request);
}
