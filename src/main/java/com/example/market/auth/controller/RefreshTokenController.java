package com.example.market.auth.controller;

import com.example.market.auth.dto.AccessTokenDto;
import com.example.market.auth.dto.JwtTokenDto;
import com.example.market.auth.dto.ReissuanceDto;
import com.example.market.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RefreshTokenController {
    private final RefreshTokenService refreshTokenService;
    @PostMapping("/reissue-token")
    public ResponseEntity<JwtTokenDto> reIssueAccessToken(
        @RequestBody
        AccessTokenDto dto
    ) {
        return ResponseEntity.ok(refreshTokenService.reIssueAccessToken(dto));
    }

}
