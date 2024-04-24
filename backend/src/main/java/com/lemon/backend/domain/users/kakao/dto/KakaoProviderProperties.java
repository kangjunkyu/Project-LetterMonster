package com.lemon.backend.domain.users.kakao.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "provider.kakao")
public class KakaoProviderProperties {
    private String authorizationUri;
    private String tokenUri;
    private String userInfoUri;
    private String logoutUri;
}
