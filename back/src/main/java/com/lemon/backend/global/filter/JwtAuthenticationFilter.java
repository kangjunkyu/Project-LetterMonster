package com.lemon.backend.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lemon.backend.global.exception.CustomException;
import com.lemon.backend.global.exception.ErrorCode;
import com.lemon.backend.global.exception.ErrorResponseEntity;
import com.lemon.backend.global.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    private final static String main = "/";
    private final static List<String> whiteList = new ArrayList<>();
    static {
        //jwt 토큰이 필요 없는 곳은 uri 추가
        whiteList.add("/api/login/oauth2/code");
        whiteList.add("/api/user/token");
        whiteList.add("/api/user/login");
        whiteList.add("/api/swagger-ui");
        whiteList.add("/api/v3/api-docs");
        whiteList.add("/api/actuator/health");

        whiteList.add("/api/user/public");
        whiteList.add("/api/sketchbooks/public");
        whiteList.add("/api/characters/public");
        whiteList.add("/api/letter/public");
        whiteList.add("/api/actuator/prometheus");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();

        log.info(requestURI);

        if (requestURI.equals("/") || checkWhiteList(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String bearerToken = httpRequest.getHeader("Authorization");

            if (bearerToken != null && !bearerToken.isEmpty()) {
                String accessToken = jwtTokenProvider.resolveToken(bearerToken);
                if (jwtTokenProvider.validateToken(accessToken)) {
                    Integer userId = jwtTokenProvider.getSubject(accessToken);
                    String role = jwtTokenProvider.getRoleFromToken(accessToken);

                    List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
                    Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            chain.doFilter(request, response);
        } catch (CustomException e) {
            setErrorResponse((HttpServletResponse) response, e.getErrorCode());
        }
        catch (Exception e) {
            log.error("Error processing authentication", e);
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Error");
        }
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode ec) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(ec.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8");
        ErrorResponseEntity errorResponseEntity = ErrorResponseEntity.builder()
                .status(ec.getStatus().value())
                .message(ec.getMessage())
                .build();
        try {
            response.getWriter().write(objectMapper.writeValueAsString(errorResponseEntity));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkWhiteList(String requestURI) {
        for (String white : whiteList) {
            if(requestURI.startsWith(white)) {
                return true;
            }
        }
        return false;
    }
}
