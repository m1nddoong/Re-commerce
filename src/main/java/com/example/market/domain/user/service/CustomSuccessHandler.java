package com.example.market.domain.user.service;

import com.example.market.domain.user.entity.RefreshToken;
import com.example.market.domain.user.repository.RefreshTokenRepository;
import com.example.market.domain.user.jwt.JwtTokenUtils;
import com.example.market.domain.user.jwt.TokenType;
import com.example.market.domain.user.dto.PrincipalDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenUtils jwtTokenUtils;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        // principal 정보 가져오기
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String email = principalDetails.getName();

        // jwt 토큰 발급
        String accessToken = jwtTokenUtils.createJwt(email, TokenType.ACCESS);
        String refreshToken = jwtTokenUtils.createJwt(email, TokenType.REFRESH);

        // Redis에 'uuid - refreshToken' 저장
        refreshTokenRepository.save(RefreshToken.builder()
                .uuid(String.valueOf(principalDetails.getUser().getUuid()))
                .refreshToken(refreshToken)
                .build());

        response.addCookie(createCookie("Authorization", accessToken));
        // 로그인 성공 후 프론트 측 특정 URL 로 리다이렉팅
        response.sendRedirect("http://localhost:3000/");
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge((int) TokenType.ACCESS.getTokenValidMillis());
        // cookie.setSecure(true); // https 에서만 쿠키를 사용할 수 있도록 설정
        cookie.setPath("/");
        cookie.setHttpOnly(true); // javaScript 가 쿠키를 가져가지 못하게

        return cookie;
    }
}
