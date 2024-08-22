package com.example.market.auth.controller.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class JwtTokenDto {
    private String uuid;
    private String accessToken;
    private String refreshToken;
    // 이게 지금 accessToken 에 대한 만료시간인지, refreshToken 에 대한 만료시간인지 구분이 안되어있음
    // 일단 이건 지금 accessToken 만료시간이라고 지정함
    private LocalDateTime expiredDate; // 토큰 만료 날짜
    private Long expiredSecond; // 토큰 만료 시간 (초단위)
}
