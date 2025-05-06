package com.studydashboard.api.auth.service;

import com.studydashboard.api.auth.dto.LoginRequestDto;
import com.studydashboard.api.auth.dto.RegisterRequestDto;
import com.studydashboard.api.auth.dto.TokenResponseDto;
import com.studydashboard.api.auth.provider.JwtProvider;
import com.studydashboard.api.user.dto.CreateUserRequestDto;
import com.studydashboard.api.user.dto.FindUserRequestDto;
import com.studydashboard.api.user.entity.User;
import com.studydashboard.api.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthService {
    private final JwtProvider jwtProvider;
    private final UserService userService;

    @Transactional
    public TokenResponseDto register(final RegisterRequestDto dto){
        User user = userService.create(new CreateUserRequestDto(dto.email(), dto.password(), dto.name()));
        return jwtProvider.generateToken(user);
    }

    @Transactional
    public TokenResponseDto login(final LoginRequestDto dto){
        User user = userService.find(new FindUserRequestDto(dto.email(), dto.password()));
        return jwtProvider.generateToken(user);
    }

    @Transactional
    public TokenResponseDto getAccessTokenByRefreshToken(String refreshToken){
        return jwtProvider.generateTokenByRefreshToken(refreshToken);
    }
}
