package com.example.market.global.jwt;

import com.example.market.global.oauth2.PrincipalDetails;
import com.example.market.domain.user.service.CustomUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenUtils jwtTokenUtils;
    private final CustomUserService customUserService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // 쿠키에 담긴 Authorization값 Token으로 받아오고 검증하기
        String accessToken = getTokenFromCookie(request);
        if (accessToken == null) {
            log.info("토큰이 정보가 없습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 소멸 시간 검증
        if (jwtTokenUtils.isExpired(accessToken)) {
            log.info("토큰이 만료되었습니다. 다시 로그인 하세요");
            filterChain.doFilter(request, response);
        }

        setAuthentication(accessToken);
        filterChain.doFilter(request, response);
    }

    private String getTokenFromCookie(HttpServletRequest request) {
        String authorization = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authorization")) {
                    authorization = cookie.getValue();
                    log.info("authorization : {} ", authorization);
                }
            }
        } else {
            log.info("No cookies found");
        }
        return authorization;

    }

    private void setAuthentication(String accessToken) {
        String email = jwtTokenUtils.getEmail(accessToken);
        PrincipalDetails principalDetails = customUserService.loadUserByUsername(email);
        Authentication authentication = jwtTokenUtils.getAuthentication(principalDetails);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("현재 사용자 : {}", SecurityContextHolder.getContext().getAuthentication().getName());
        log.info("이메일 : {}", email);
        log.info("권한 : {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
    }
}


