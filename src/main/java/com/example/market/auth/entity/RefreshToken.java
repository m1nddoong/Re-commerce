package com.example.market.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash(value = "RefreshToken", timeToLive = 60 * 60 * 24) // 24시간 이후 자동 삭제
public class RefreshToken {
    @Id
    private String uuid;
    private String refreshToken;
}
