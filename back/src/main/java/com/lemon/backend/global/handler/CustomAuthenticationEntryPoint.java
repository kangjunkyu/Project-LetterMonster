package com.lemon.backend.global.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver resolver;

    public CustomAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        if (response.getStatus() == HttpServletResponse.SC_OK) {
            return;
        }

        Exception exception;
        switch (response.getStatus()) {
            case HttpServletResponse.SC_NOT_FOUND:
                exception = new NoSuchElementException("요청하신 리소스를 찾을 수 없습니다.");
                break;
            case HttpServletResponse.SC_UNAUTHORIZED:
            case HttpServletResponse.SC_FORBIDDEN:
                exception = (Exception) request.getAttribute("exception");
                break;
            case HttpServletResponse.SC_REQUEST_TIMEOUT:
                exception = new TimeoutException("요청 시간이 초과되었습니다.");
                break;
            default:
                exception = new Exception("서버에 문제가 발생했습니다.");
                break;
        }

        resolver.resolveException(request, response, null, exception);
    }
}
