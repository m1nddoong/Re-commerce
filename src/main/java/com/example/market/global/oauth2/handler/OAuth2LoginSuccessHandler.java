package com.example.market.global.oauth2.handler;

import static com.example.market.domain.user.constant.Role.INACTIVE_USER;
import static com.example.market.global.util.CookieUtil.createCookie;

import com.example.market.domain.user.constant.Role;
import com.example.market.domain.user.entity.RefreshToken;
import com.example.market.domain.user.repository.RefreshTokenRepository;
import com.example.market.global.jwt.JwtTokenUtils;
import com.example.market.global.jwt.TokenType;
import com.example.market.global.oauth2.PrincipalDetails;
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

        // jwt 토큰 발급
        String accessToken = jwtTokenUtils.createJwt(email, TokenType.ACCESS);
        String refreshToken = jwtTokenUtils.createJwt(email, TokenType.REFRESH);

        // Redis에 'uuid - refreshToken' 저장
        refreshTokenRepository.save(RefreshToken.builder()
                .uuid(String.valueOf(principalDetails.getUser().getUuid()))
                .refreshToken(refreshToken)
                .build());
        response.addCookie(createCookie("Authorization", accessToken));

        // INACTIVE 인 User 의 경우 처음 로그인 요청한 회원이므로 회원가입 페이지로 리다이렉트
        if (principalDetails.getUser().getRole() == INACTIVE_USER) {
            // 추가 회원가입 폼으로 이동
            response.sendRedirect("http://localhost:3000/profile-update");
        } else {
            response.sendRedirect("http://localhost:3000/");
        }
    }
}
