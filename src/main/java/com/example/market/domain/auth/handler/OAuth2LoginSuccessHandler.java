package com.example.market.domain.auth.handler;

import static com.example.market.global.util.CookieUtil.createCookie;

import com.example.market.domain.auth.entity.RefreshToken;
import com.example.market.domain.auth.repository.RefreshTokenRepository;
import com.example.market.domain.auth.jwt.JwtTokenUtils;
import com.example.market.domain.auth.jwt.TokenType;
import com.example.market.domain.auth.entity.PrincipalDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenUtils jwtTokenUtils;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");

        // principal 정보 가져오기
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String email = principalDetails.getName();

        // jwt 토큰 발급 및 쿠키에 저장
        String accessToken = jwtTokenUtils.createJwt(email, TokenType.ACCESS);
        String refreshToken = jwtTokenUtils.createJwt(email, TokenType.REFRESH);
        response.addCookie(createCookie("Authorization", accessToken));

        // Redis에 'uuid - refreshToken' 저장
        refreshTokenRepository.save(RefreshToken.builder()
                .uuid(String.valueOf(principalDetails.getUser().getUuid()))
                .refreshToken(refreshToken)
                .build());

        // .로그인 성공 후 리다이렉팅 페이지
        response.sendRedirect("http://localhost:3000/");
    }
}
