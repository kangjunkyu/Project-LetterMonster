package com.lemon.backend.global.auth;

import com.lemon.backend.global.cookie.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.Cookie;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import static com.lemon.backend.global.auth.OAuth2AuthorizationRequestBasedOnCookieRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
@RequiredArgsConstructor
public class OAuth2LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String targetUrl = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse(("/"));

        exception.printStackTrace();

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", exception.getLocalizedMessage())
                .build().toUriString();

        authorizationRequestRepository.removeAuthorizationRequest(request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
