package com.lemon.backend.global.config;

import com.lemon.backend.global.auth.CustomOAuth2UserService;
import com.lemon.backend.global.auth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.lemon.backend.global.auth.OAuth2LoginFailureHandler;
import com.lemon.backend.global.auth.OAuth2LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 기본 인증 설정 비활성화
                .formLogin(form -> form.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .csrf(csrf -> csrf.disable()) // CSRF 보호 기능 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정
                .headers(headers -> headers.frameOptions().disable()) // X-Frame-Options 비활성화 -> h2 console을 위해서 사용

                // 권한 요청 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login/oauth2/code")
                        .permitAll() // 명시된 경로에 대한 요청은 인증 없이 허용
                        .anyRequest().permitAll())

                // OAuth2 로그인 구성
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(auth -> auth
                                .baseUri("/oauth2/authorization") // 이 때, 사용자 인증코드 (authorization code)를 함께 갖고감
                                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository()))//Oauth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정 담당
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))// 소셜 로그인 성공 시 후속조치를 진행할 UserService인터페이스의 구현체 등록
                        .successHandler(oAuth2LoginSuccessHandler)
                        .failureHandler(oAuth2LoginFailureHandler(oAuth2AuthorizationRequestBasedOnCookieRepository())));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*"); // 허용할 Origin 설정, *은 모든 Origin을 허용하는 것이므로 실제 환경에서는 제한 필요
        configuration.addAllowedMethod("*"); // 허용할 HTTP Method 설정
        configuration.addAllowedHeader("*"); // 허용할 HTTP Header 설정
        configuration.addExposedHeader("Authorization");
        configuration.addExposedHeader("Authorization-refresh");
        configuration.setAllowCredentials(false); // Credentials를 사용할지 여부 설정

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 CORS 설정 적용

        return source;
    }

    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    @Bean
    public OAuth2LoginFailureHandler oAuth2LoginFailureHandler(OAuth2AuthorizationRequestBasedOnCookieRepository repository) {
        return new OAuth2LoginFailureHandler(repository);
    }
}
