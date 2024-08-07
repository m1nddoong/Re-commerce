package com.example.market.auth.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class JwtTokenDto {
    private String uuid;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime expiredDate;
    private Long expiredSecond;
}
