package com.studydashboard.api.user.dto;

public record CreateUserRequestDto(String email, String password, String name) {
}
