package com.studydashboard.api.auth.provider;

import com.studydashboard.api.auth.dto.TokenResponseDto;
import com.studydashboard.api.auth.entity.RefreshToken;
import com.studydashboard.api.auth.repository.RefreshTokenRepository;
import com.studydashboard.api.user.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
public class JwtProvider {
    private final String secrets;
    private final Key key;
    private final RefreshTokenRepository refreshTokenRepository;
    private static final long ACCESS_TOKEN_EXPIRATION_MILLIS = 1000 * 60 * 60;
    private static final long REFRESH_TOKEN_EXPIRATION_MILLIS = 1000 * 60 * 60 * 24;

    public JwtProvider(@Value("${jwt.secrets}") String secret, RefreshTokenRepository refreshTokenRepository) {
        this.secrets = secret;
        byte[] keyBytes = Decoders.BASE64.decode(secrets);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public Boolean validateToken(final String token) {
        try {
            getClaimsFromToken(token);
            return true;
        } catch (SecurityException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            return false;
        }
    }

    public TokenResponseDto generateToken(final User user) {
        return TokenResponseDto.builder()
                .accessToken(generateAccessToken(user))
                .refreshToken(generateRefreshToken(user))
                .build();
    }

    public TokenResponseDto generateTokenByRefreshToken(final String token) {
        try {
            Claims claims = getClaimsFromToken(token);

            String refreshTokenSubject = claims.getSubject();
            Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findRefreshTokenByToken(refreshTokenSubject);

            if (optionalRefreshToken.isEmpty()) {
                throw new IllegalArgumentException("리프레시 토큰이 유효하지 않습니다.");
            }

            RefreshToken refreshToken = optionalRefreshToken.get();

            return TokenResponseDto.builder()
                    .accessToken(generateAccessToken(refreshToken.getUser()))
                    .refreshToken(generateRefreshToken(refreshToken.getUser()))
                    .build();

        } catch (SecurityException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            throw new IllegalArgumentException("리프레시 토큰이 유효하지 않습니다.");
        }
    }

    public Claims getClaimsFromToken(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String generateAccessToken(final User user) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + ACCESS_TOKEN_EXPIRATION_MILLIS))
                .signWith(key)
                .compact();
    }

    private String generateRefreshToken(final User user) {
        long now = System.currentTimeMillis();
        String subject = UUID.randomUUID().toString();
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findRefreshTokenByUser(user);

        RefreshToken refreshToken;

        if (optionalRefreshToken.isPresent()) {
            refreshToken = optionalRefreshToken.get();
            refreshToken.setToken(subject);
        } else {
            refreshToken = RefreshToken.builder()
                    .user(user)
                    .token(subject)
                    .build();
        }

        refreshTokenRepository.save(refreshToken);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRATION_MILLIS))
                .signWith(key)
                .compact();
    }
}