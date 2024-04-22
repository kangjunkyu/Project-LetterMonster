package com.lemon.backend.global.config;

import com.lemon.backend.domain.users.user.repository.UserRepository;
import com.lemon.backend.global.filter.CorsFilter;
import com.lemon.backend.global.filter.JwtAuthenticationFilter;
import com.lemon.backend.global.jwt.JwtTokenProvider;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
//    @Bean
//    public FilterRegistrationBean corsFilter() {
//        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
//        filterRegistrationBean.setFilter(new CorsFilter());
//        filterRegistrationBean.setOrder(1);
//        return filterRegistrationBean;
//    }
//
//    @Bean
//    public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
//        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new JwtAuthenticationFilter(jwtTokenProvider));
//        registrationBean.setOrder(2);
//        registrationBean.addUrlPatterns("/*");
//        return registrationBean;
//    }

}
