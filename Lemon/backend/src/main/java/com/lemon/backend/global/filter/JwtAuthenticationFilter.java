package com.lemon.backend.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lemon.backend.global.exception.CustomException;
import com.lemon.backend.global.exception.ErrorCode;
import com.lemon.backend.global.exception.ErrorResponseEntity;
import com.lemon.backend.global.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final static String main = "/";
    private final static List<String> whiteList = new ArrayList<>();
    static {
        //jwt 토큰이 필요 없는 곳은 uri 추가
        whiteList.add("/api/kakao");
        whiteList.add("/api/swagger-ui");
        whiteList.add("/api/v3/api-docs");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestURI = request.getRequestURI();

        //main url 이거나 whiteList url이면 다음 필터로
        if (requestURI.equals(main) || checkWhiteList(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        try{
            String accessToken = resolveToken(request);

            //회원인 경우
            if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
                // 토큰이 유효할 경우
                request.setAttribute("userId", jwtTokenProvider.getSubject(accessToken));
                chain.doFilter(request, response);
            }
        }catch (StringIndexOutOfBoundsException e) {
            throw new CustomException(ErrorCode.NOT_FOUND_AUTH_TOKEN);
        } catch (CustomException e) {
            setErrorResponse(response, e.getErrorCode());
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

    // 헤더에서 토큰 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
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
