package com.lemon.backend.domain.users.user.service;

import com.lemon.backend.domain.users.kakao.dto.KakaoProfile;
import com.lemon.backend.domain.users.user.entity.Social;
import com.lemon.backend.domain.users.user.entity.Users;

public interface UserService {
    public String makeNickname();
    public Users createKakaoUser(KakaoProfile profile, Social social);
}
