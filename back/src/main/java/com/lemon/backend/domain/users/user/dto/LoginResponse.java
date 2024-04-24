package com.lemon.backend.domain.users.user.dto;

import com.lemon.backend.global.jwt.TokenResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class LoginResponse {
    public TokenResponse token;
    public Boolean isLanguageSet;
    public String nickname;
    public String nicknameTag;
}
