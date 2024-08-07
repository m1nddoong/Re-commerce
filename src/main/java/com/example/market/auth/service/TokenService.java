package com.example.market.auth.service;

import com.example.market.auth.dto.JwtTokenDto;
import com.example.market.auth.dto.ReissuanceDto;
import com.example.market.auth.entity.RefreshToken;
import com.example.market.auth.entity.User;
import com.example.market.auth.jwt.JwtTokenUtils;
import com.example.market.auth.jwt.TokenType;
import com.example.market.auth.repo.RefreshTokenRepository;
import com.example.market.auth.repo.UserRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Transactional
@Service
public class TokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtTokenUtils jwtTokenUtils;

    public JwtTokenDto reIssuanceAccessToken(ReissuanceDto dto) {
        // uuid 를 기준으로 저장된 리프레시 토큰 가져오기
        RefreshToken storedRefreshToken = refreshTokenRepository.findById(dto.getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // redis 에서 가져온 refreshToken을 활용하여
        User user = userRepository.findUserByUuid(UUID.fromString(storedRefreshToken.getUuid()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // 새로운 토큰 생성
        String newAccessToken = jwtTokenUtils.generateToken(user, TokenType.ACCESS);
        String newRefreshToken = jwtTokenUtils.generateToken(user, TokenType.REFRESH);

        // 기존 리프레시 토큰 삭제 후 새로운 토큰 redis 에 저장
        refreshTokenRepository.deleteById(dto.getUuid());
        refreshTokenRepository.save(RefreshToken.builder()
                .refreshToken(newRefreshToken)
                .uuid(dto.getUuid())
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
