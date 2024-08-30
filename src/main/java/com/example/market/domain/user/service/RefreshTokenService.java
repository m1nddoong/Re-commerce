package com.example.market.domain.user.service;

import com.example.market.global.jwt.JwtTokenDto;
import com.example.market.domain.user.entity.RefreshToken;
import com.example.market.domain.user.entity.User;
import com.example.market.global.jwt.JwtTokenUtils;
import com.example.market.global.jwt.TokenType;
import com.example.market.domain.user.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
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
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationFacade authFacade;

    /**
     * accessToken, refreshToken 재발급
     */
    public JwtTokenDto reIssueJwtToken() {
        // 현재 인증된 사용자 정보 가져오기
        User currentUser = authFacade.extractUser();
        String uuid = String.valueOf(currentUser.getUuid());
        String email = currentUser.getEmail();

        // uuid 로 refreshToken 조회
        RefreshToken storedRefreshToken = refreshTokenRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // 사용자 정보를 바탕으로 새로운 accessToken, refreshToken 생성
        String newAccessToken = jwtTokenUtils.createJwt(email, TokenType.ACCESS);
        String newRefreshToken = jwtTokenUtils.createJwt(email, TokenType.REFRESH);

        // 기존 refreshToken 삭제, 새 refreshToken을 redis 에 저장
        refreshTokenRepository.delete(storedRefreshToken);
        refreshTokenRepository.save(RefreshToken.builder()
                .uuid(uuid)
                .refreshToken(newRefreshToken)
                .build()
        );


        return JwtTokenDto.builder()
                .uuid(uuid)
                .accessToken(newAccessToken)
                .expiredDate(LocalDateTime.now().plusSeconds(TokenType.ACCESS.getTokenValidMillis() / 1000))
                .expiredSecond(TokenType.ACCESS.getTokenValidMillis() / 1000)
                .build();
    }
}
