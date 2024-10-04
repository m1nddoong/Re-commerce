package com.example.market.domain.auth.entity;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@Getter
@AllArgsConstructor
@Builder
@RedisHash(value = "refresh_token", timeToLive = 60*60*24*3) // 7일
public class RefreshToken {
    @Id
    private String refreshToken;
    @Indexed
    private String accessToken;
    private Long userId;

    @Transactional
    public void updateRefreshToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
