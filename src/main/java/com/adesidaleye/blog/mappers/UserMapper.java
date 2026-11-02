package com.adesidaleye.blog.mappers;

import com.adesidaleye.blog.domain.dtos.RegisterRequest;
import com.adesidaleye.blog.domain.dtos.UserDto;
import com.adesidaleye.blog.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(RegisterRequest userDto);
}
