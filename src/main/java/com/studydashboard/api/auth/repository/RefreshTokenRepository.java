package com.studydashboard.api.auth.repository;

import com.studydashboard.api.auth.entity.RefreshToken;
import com.studydashboard.api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findRefreshTokenByUser(User user);
    Optional<RefreshToken> findRefreshTokenByToken(String token);
}
