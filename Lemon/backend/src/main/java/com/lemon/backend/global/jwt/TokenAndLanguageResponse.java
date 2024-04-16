package com.lemon.backend.global.jwt;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenAndLanguageResponse {
    private String accessToken;
    private String refreshToken;
    private String grantType;
    private Boolean isLanguageSet;

    @Builder
    public TokenAndLanguageResponse(String accessToken, String refreshToken, String grantType) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.grantType = grantType;
    }
}