package com.example.market.auth.controller;

import com.example.market.auth.dto.JwtTokenDto;
import com.example.market.auth.dto.ReissuanceDto;
import com.example.market.auth.entity.RefreshToken;
import com.example.market.auth.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    @PostMapping("/token")
    public ResponseEntity<JwtTokenDto> reIssuanceAccessToken(
        @RequestBody
        ReissuanceDto reissuanceDto
    ) {
        return ResponseEntity.ok(tokenService.reIssuanceAccessToken(reissuanceDto));
    }

}
