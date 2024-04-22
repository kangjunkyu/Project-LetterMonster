package com.lemon.backend.global.redis.entity;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@RedisHash("TokenBlacklist")
public class TokenBlacklist {

    @Id
    private String token;
    private Long expirationTime;
}
