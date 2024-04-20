package com.lemon.backend.global.auth.userinfo;

import com.lemon.backend.domain.users.user.entity.Social;
import com.lemon.backend.global.auth.userinfo.KakaoOAuth2UserInfo;
import com.lemon.backend.global.auth.userinfo.OAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory{
    public static OAuth2UserInfo getOAuth2UserInfo(Social social, Map<String, Object> attributes) {
        switch (social) {
            case KAKAO:
                return new KakaoOAuth2UserInfo(attributes);
            case LINE:
//                return new FacebookOAuth2UserInfo(attributes);
            default:
                throw new IllegalArgumentException("Unsupported Social " + social);
        }
    }
}
