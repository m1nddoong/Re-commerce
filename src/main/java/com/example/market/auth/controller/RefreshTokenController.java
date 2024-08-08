//package com.example.market.auth.controller;
//
//import com.example.market.auth.dto.JwtTokenDto;
//import com.example.market.auth.dto.ReissuanceDto;
//import com.example.market.auth.service.RefreshTokenService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//public class RefreshTokenController {
//    private final RefreshTokenService refreshTokenService;
//
//    @PostMapping("/token")
//    public ResponseEntity<JwtTokenDto> reIssuanceAccessToken(
//        @RequestBody
//        ReissuanceDto reissuanceDto
//    ) {
//        return ResponseEntity.ok(refreshTokenService.reIssuanceAccessToken(reissuanceDto));
//    }
//
//}
