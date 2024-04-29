package com.lemon.backend.global.auth;

import com.lemon.backend.domain.users.user.entity.Social;
import com.lemon.backend.domain.users.user.entity.Users;
import com.lemon.backend.domain.users.user.repository.UserRepository;
import com.lemon.backend.domain.users.user.service.UserService;
import com.lemon.backend.global.auth.userinfo.OAuth2UserInfo;
import com.lemon.backend.global.auth.userinfo.OAuth2UserInfoFactory;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauthUser = super.loadUser(userRequest);

        Social provider = Social.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(provider, oauthUser.getAttributes());
        Users user = processUserLogin(userInfo, provider);

        return UserPrincipal.create(user, oauthUser.getAttributes());
    }

    @Transactional
    public Users processUserLogin(OAuth2UserInfo userInfo, Social provider) {

        String providerId = userInfo.getId(); // UserInfo 객체를 통해 ID 가져오기

        Users user = userRepository.findByProviderAndProviderIdIgnoreDeleted(provider.name(), providerId)
                .orElseGet(() -> userService.createUser(userInfo, provider));
        if (user.getIsDeleted()) {
            userRepository.updateIsDeleted(user.getId());
        }

        return user;
    }
}
