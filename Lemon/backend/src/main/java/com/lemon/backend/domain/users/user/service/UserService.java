package com.lemon.backend.domain.users.user.service;

import com.lemon.backend.domain.users.kakao.dto.KakaoProfile;
import com.lemon.backend.domain.users.user.dto.request.ChangeNicknameRequest;
import com.lemon.backend.domain.users.user.dto.response.ChangeNicknameResponse;
import com.lemon.backend.domain.users.user.dto.response.UserGetDto;
import com.lemon.backend.domain.users.user.entity.Social;
import com.lemon.backend.domain.users.user.entity.Users;
import com.lemon.backend.global.jwt.TokenResponse;

public interface UserService {
    String makeNickname();
    Users createKakaoUser(KakaoProfile profile, Social social);
    TokenResponse recreateToken(String bearerToken);
    void saveRefreshTokenIntoRedis(Integer userId, String token);
    void logout(Integer userId);
    ChangeNicknameResponse changeNickname(Integer userId, ChangeNicknameRequest request);
    void withdrawUser(Integer userId);
    String getSocialLoginUrl(String provider);
    UserGetDto getUserInfo(Integer userId);
}
