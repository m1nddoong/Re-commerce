package com.example.market.domain.auth.controller;

import com.example.market.domain.auth.jwt.JwtTokenDto;
import com.example.market.domain.auth.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "redis", description = "토큰 재발급 API")
@RestController
@RequestMapping("/api/v1/token")
@RequiredArgsConstructor
public class RefreshTokenController {
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/refresh")
    @Operation(
            summary = "accessToken 재발급",
            description = "<p>현재 인증된 사용자의 uuid 부터 redis 에 저장된 accessToken 을 찾고,</p>"
                    + "<p>accessToken, refreshToken 재발급</p>"
    )
    public ResponseEntity<JwtTokenDto> reIssueJwtToken(HttpServletResponse response) {
        return ResponseEntity.ok(refreshTokenService.reIssueJwtToken(response));
    }

}
