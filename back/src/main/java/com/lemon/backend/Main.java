package com.lemon.backend;

import com.lemon.backend.domain.characters.service.impl.CharacterServiceImpl;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableJpaAuditing
@EnableConfigurationProperties
@SpringBootApplication
@EnableScheduling

public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}