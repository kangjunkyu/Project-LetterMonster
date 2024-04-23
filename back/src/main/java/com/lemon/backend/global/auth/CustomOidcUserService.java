package com.lemon.backend.global.auth;

import com.lemon.backend.domain.users.user.entity.Social;
import com.lemon.backend.domain.users.user.entity.Users;
import com.lemon.backend.global.auth.userinfo.OAuth2UserInfo;
import com.lemon.backend.global.auth.userinfo.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.core.AuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {

    private final CustomOAuth2UserService customOAuth2UserService;
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        Social provider = Social.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(provider, oidcUser.getAttributes());
        Users user = customOAuth2UserService.processUserLogin(userInfo, provider);

        return UserPrincipal.create(user, oidcUser.getAttributes());
    }
}
