package com.lemon.backend.global.redis.entity;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;


@RedisHash("refreshToken")
@Getter
@Setter
public class RefreshToken {
    @Id
    private Integer id;
    private String token;
}
