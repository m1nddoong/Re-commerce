package com.example.market.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
// Redis Lettuce 사용하기 위함
// Redis 에서 RefreshToken 객체가 TokenType.REFRESH 와 동일한 24시간 이후 자동 삭제
@RedisHash(value = "jwtToken", timeToLive = 60 * 60 * 24)
@AllArgsConstructor
@Builder
public class RefreshToken {
    @Id
    private String userUuid;
    private String refreshToken;
    // Redis의 보조 인덱스 생성에 사용 되며,
    // @Id 가 붙여진 객체 외에도 @Indexed가 붙여진 객체로도 값을 조회할 수 있다.
    // 즉 uuid 값 뿐 아니라 accessToken 으로도 데이터를 검색할 수 있다.
    @Indexed
    private String accessToken;
}
