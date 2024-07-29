package com.example.market.auth.jwt;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponseDto {
    private String uuid;
    // accessToken
    private String accessToken;

    // 만료일
    private LocalDateTime expiredDate;

    // 토큰 만료 시간
    private Integer expiredSecond;
}
