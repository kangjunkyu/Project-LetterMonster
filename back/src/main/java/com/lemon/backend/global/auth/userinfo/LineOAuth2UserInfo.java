package com.lemon.backend.global.auth.userinfo;

import java.util.Map;

public class LineOAuth2UserInfo extends OAuth2UserInfo {

    public LineOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return String.valueOf(attributes.get("sub"));
    }
}
