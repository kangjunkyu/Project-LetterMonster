package com.lemon.backend.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
//import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.info.Info;

//@OpenAPIDefinition(
//        info = @Info(title = "Lemon API 명세서",
//                description = "Lemon 서비스 API 명세서",
//                version = "v1"))
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        String jwt = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
                .name(jwt)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
        );
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo())
                .addSecurityItem(securityRequirement)
                .components(components);
    }

    private Info apiInfo() {
        return new Info()
                .title("Lemon") // API의 제목
                .description("LetterMonster의 API 문서입니다") // API에 대한 설명
                .version("1.0.0"); // API의 버전
    }

//    @Bean
//    public GroupedOpenApi all() {
//        return GroupedOpenApi.builder()
//                .group("전체").pathsToMatch("/api/**").build();
//    }
//
//    @Bean
//    public GroupedOpenApi characterGroup() {
//        return GroupedOpenApi.builder()
//                .group("캐릭터").pathsToMatch("/api/characters/**").build();
//    }
//
//    @Bean
//    public GroupedOpenApi characterMotionGroup() {
//        return GroupedOpenApi.builder()
//                .group("캐릭터모션").pathsToMatch("/api/charactermotions/**").build();
//    }
//
//    @Bean
//    public GroupedOpenApi letterGroup() {
//        return GroupedOpenApi.builder()
//                .group("편지").pathsToMatch("/api/letters/**").build();
//    }
//
//    @Bean
//    public GroupedOpenApi motionGroup() {
//        return GroupedOpenApi.builder()
//                .group("모션").pathsToMatch("/api/motions/**").build();
//    }
//    @Bean
//    public GroupedOpenApi sketchbookGroup() {
//        return GroupedOpenApi.builder()
//                .group("스케치북").pathsToMatch("/api/sketchbooks/**").build();
//    }
//
//    @Bean
//    public GroupedOpenApi usersGroup() {
//        return GroupedOpenApi.builder()
//                .group("유저").pathsToMatch("/api/users/**").build();
//    }

}
