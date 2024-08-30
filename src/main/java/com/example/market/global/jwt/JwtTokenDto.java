package com.example.market.global.jwt;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class JwtTokenDto {
    private String uuid;
    private String accessToken;
    private LocalDateTime expiredDate; // 토큰 만료 날짜
    private Long expiredSecond; // 토큰 만료 시간 (초단위)
}
