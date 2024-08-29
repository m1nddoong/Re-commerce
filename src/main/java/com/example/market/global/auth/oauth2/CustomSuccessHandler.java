package com.example.market.global.auth.oauth2;

import com.example.market.global.auth.jwt.JwtTokenUtils;
import com.example.market.global.auth.jwt.TokenType;
import com.example.market.global.auth.oauth2.dto.PrincipalDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenUtils jwtTokenUtils;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        // principal 뽑기
        PrincipalDetails customUserDetails = (PrincipalDetails) authentication.getPrincipal();
        String email = customUserDetails.getEmail();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String token = jwtTokenUtils.createJwt(email, TokenType.ACCESS);

        response.addCookie(createCookie("Authorization", token));

        // 로그인 성공 후 프론트 측 특정 URL 로 리다이렉팅 되도록
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
