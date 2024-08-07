package com.example.market.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
// Redis Lettuce 사용하기 위함
// Redis 에서 RefreshToken 객체가 TokenType.REFRESH 와 동일한 24시간 이후 자동 삭제
@RedisHash (value = "refreshToken", timeToLive = 86400)
@AllArgsConstructor
@Builder
public class RefreshToken {
    @Id
    private String refreshToken;
    private String uuid;
}
