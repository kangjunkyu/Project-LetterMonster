package com.lemon.backend.global.auth;

import com.lemon.backend.global.cookie.CookieUtil;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

/*
 * oauth2를 이용한 로그인을 구현할 때 관련된 필요한 정보들을 쿠키에 임시로 저장한다.
 * 상태 유지를 하기 위해서 저장함
 * */

@Slf4j
public class OAuth2AuthorizationRequestBasedOnCookieRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
    public static final String FIREBASE_TOKEN_COOKIE_NAME = "firebase_token";
    private static final int cookieExpireSeconds = 180;

    //쿠키 가져옴
    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {

        return CookieUtil.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
                .map(cookie -> CookieUtil.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElse(null);
    }

    ///프론트에서 oauth2/authorization/{provider}?redirect_uri=<redirect-uri-after-login>와 같이 요청이 오면
    // redirect uri도 쿠키에 함께 저장한다.
    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        log.info("쿠키 저장해");
        //쿠키가 있는 상태이면 삭제함.
        if (authorizationRequest == null) {
            log.info("쿠키 삭제");
            CookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
            CookieUtil.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
            if (request.getParameter(FIREBASE_TOKEN_COOKIE_NAME) == null) {
                log.info("파이어베이스 토큰 쿠키 삭제");
                CookieUtil.deleteCookie(request, response, FIREBASE_TOKEN_COOKIE_NAME);
            }
            return;
        }

        log.info("쿠키 첨부");
        // 쿠키에 REDIRECT URL 첨부
        CookieUtil.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, CookieUtil.serialize(authorizationRequest), cookieExpireSeconds);
        String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);
        if (request.getParameter(FIREBASE_TOKEN_COOKIE_NAME) != null) {
            String firebaseTokenAfterLogin = request.getParameter(FIREBASE_TOKEN_COOKIE_NAME);
            CookieUtil.addCookie(response, FIREBASE_TOKEN_COOKIE_NAME, firebaseTokenAfterLogin, cookieExpireSeconds);
            log.info("파이어베이스 토큰 진짜 있음");
        }
        if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
            log.info("쿠키 진짜 넣음");
            CookieUtil.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, redirectUriAfterLogin, cookieExpireSeconds);
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        return this.loadAuthorizationRequest(request);
    }
}
