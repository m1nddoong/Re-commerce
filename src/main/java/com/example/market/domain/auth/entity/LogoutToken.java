package com.example.market.domain.auth.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@AllArgsConstructor
@RedisHash(value = "black_list", timeToLive = 60*30)
public class LogoutToken {
    @Id
    private String id;
    @Indexed
    private String accessToken;
}
