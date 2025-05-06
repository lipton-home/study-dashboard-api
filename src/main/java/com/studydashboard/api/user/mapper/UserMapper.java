package com.studydashboard.api.user.mapper;

import com.studydashboard.api.user.dto.UserResponseDto;
import com.studydashboard.api.user.entity.User;

public class UserMapper {
    public static UserResponseDto toDto(final User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
