package com.lemon.backend.global.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
    private String grantType;

    public static TokenResponse of(String accessToken, String refreshToken, String grantType) {
        return new TokenResponse(accessToken, refreshToken, grantType);
    }
}