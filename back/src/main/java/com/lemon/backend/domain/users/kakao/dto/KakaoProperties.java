package com.lemon.backend.domain.users.kakao.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "kakao")
public class KakaoProperties {
    private String authorizationGrantType;
    private String clientId;
    private String redirectUri;
    private String clientSecret;
}
