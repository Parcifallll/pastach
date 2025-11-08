package com.example.Pastach.dto.auth;

import lombok.Builder;

@Builder
public record JwtResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        Long expiresIn
) {
    public JwtResponse(String accessToken, String refreshToken) {
        this(accessToken, refreshToken, "Bearer", 900L); // 15 minutes
    }
}