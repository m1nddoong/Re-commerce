package com.example.market.domain.user.controller;

import com.example.market.domain.user.dto.AccessTokenDto;
import com.example.market.domain.user.dto.JwtTokenDto;
import com.example.market.domain.user.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "redis", description = "토큰 재발급 API")
@RestController
@RequestMapping("/api/v1/token")
@RequiredArgsConstructor
public class RefreshTokenController {
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/reissue-token")
    @Operation(
            summary = "accessToken 재발급",
            description = "<p>uuid 로 부터 redis 에 저장된 accessToken 을 찾고,</p>"
                    + "<p>accessToken, refreshToken 재발급</p>"
    )
    public ResponseEntity<JwtTokenDto> reIssueAccessToken(
            @RequestBody
            AccessTokenDto dto
    ) {
        return ResponseEntity.ok(refreshTokenService.reIssueAccessToken(dto));
    }

}
