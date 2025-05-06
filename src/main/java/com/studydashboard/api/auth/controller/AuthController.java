package com.studydashboard.api.auth.controller;

import com.studydashboard.api.auth.dto.LoginRequestDto;
import com.studydashboard.api.auth.dto.RegisterRequestDto;
import com.studydashboard.api.auth.dto.TokenResponseDto;
import com.studydashboard.api.auth.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<TokenResponseDto> register(@RequestBody RegisterRequestDto dto){
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping("login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto dto){
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("refresh")
    public ResponseEntity<TokenResponseDto> refresh(@RequestHeader("Refresh") String authorizationHeader) {
        String refreshToken = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(authService.getAccessTokenByRefreshToken(refreshToken));
    }
}
