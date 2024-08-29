package com.example.market.domain.user.service;

import com.example.market.domain.user.dto.AccessTokenDto;
import com.example.market.domain.user.dto.JwtTokenDto;
import com.example.market.domain.user.entity.RefreshToken;
import com.example.market.domain.user.entity.User;
import com.example.market.global.auth.jwt.JwtTokenUtils;
import com.example.market.global.auth.jwt.TokenType;
import com.example.market.domain.user.repository.RefreshTokenRepository;
import com.example.market.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtTokenUtils jwtTokenUtils;

    // access Token (만료되어서든, 만료되지 않든 - 연장 및 재발급)
    public JwtTokenDto reIssueAccessToken(AccessTokenDto dto) {
        // uuid를 기준으로 redis 로 부터 토큰 정보 가져오기
        RefreshToken storedRefreshToken = refreshTokenRepository.findById(dto.getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        // userUuid 를 가지고 사용자 조회
        User user = userRepository.findUserByUuid(UUID.fromString(dto.getUuid()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        // 사용자 정보를 바탕으로 새로운 AccessToken 생성
        // String newAccessToken = jwtTokenUtils.generateToken(user, TokenType.ACCESS);
        // String newRefreshToken = jwtTokenUtils.generateToken(user, TokenType.REFRESH);
         String newAccessToken = jwtTokenUtils.createJwt(user.getEmail(), TokenType.ACCESS);
         String newRefreshToken = jwtTokenUtils.createJwt(user.getEmail(), TokenType.REFRESH);
        // 기존 리프레시 토큰 삭제 후 새로운 토큰 redis 에 저장
        refreshTokenRepository.delete(storedRefreshToken);
        refreshTokenRepository.save(RefreshToken.builder()
                .uuid(String.valueOf(user.getUuid()))
                .refreshToken(newRefreshToken)
                .build()
        );

        // 새로운 액세스 토큰, 리프레시 토큰 반환
        return JwtTokenDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .expiredDate(LocalDateTime.now().plusSeconds(TokenType.ACCESS.getTokenValidMillis() / 1000))
                .expiredSecond(TokenType.ACCESS.getTokenValidMillis() / 1000)
                .build();
    }
}
