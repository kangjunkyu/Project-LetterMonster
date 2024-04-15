package com.lemon.backend.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Lemon API 명세서",
                description = "Lemon 서비스 API 명세서",
                version = "v1"))
@Configuration
public class SwaggerConfig {


    @Bean
    public GroupedOpenApi all() {
        return GroupedOpenApi.builder()
                .group("전체").pathsToMatch("/api/**").build();
    }

    @Bean
    public GroupedOpenApi characterGroup() {
        return GroupedOpenApi.builder()
                .group("캐릭터").pathsToMatch("/api/v1/characters/**").build();
    }

    @Bean
    public GroupedOpenApi characterMotionGroup() {
        return GroupedOpenApi.builder()
                .group("캐릭터모션").pathsToMatch("/api/v1/charactermotions/**").build();
    }

    @Bean
    public GroupedOpenApi letterGroup() {
        return GroupedOpenApi.builder()
                .group("편지").pathsToMatch("/api/v1/letters/**").build();
    }

    @Bean
    public GroupedOpenApi motionGroup() {
        return GroupedOpenApi.builder()
                .group("모션").pathsToMatch("/api/v1/motions/**").build();
    }
    @Bean
    public GroupedOpenApi sketchbookGroup() {
        return GroupedOpenApi.builder()
                .group("스케치북").pathsToMatch("/api/v1/sketchbooks/**").build();
    }

    @Bean
    public GroupedOpenApi usersGroup() {
        return GroupedOpenApi.builder()
                .group("유저").pathsToMatch("/api/v1/users/**").build();
    }

}
