package com.example.market.domain.auth.jwt;

import com.example.market.domain.auth.dto.PrincipalDetails;
import com.example.market.domain.auth.repository.LogoutTokenRepository;
import com.example.market.domain.auth.service.PrincipalDetailsService;
import com.example.market.global.error.exception.ErrorCode;
import com.example.market.global.error.exception.GlobalCustomException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
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
    private final PrincipalDetailsService principalDetailsService;
    private final LogoutTokenRepository logoutTokenRepository;

    // filter 에서 제외시킬 URL
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludePath = {
                "/swagger-ui/**",
                "/v1/api-docs/**",
                "/api/v1/auth/sign-up",
                "/api/v1/auth/sign-in"
        };
        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // 쿠키에 담긴 Authorization값 Token으로 받아오고 검증하기
        log.info("JWT 쿠키 검증 시작");
        String accessToken = jwtTokenUtils.getTokenFromCookie(request);

        if (accessToken == null || jwtTokenUtils.isExpired(accessToken)) {
            throw new ExpiredJwtException(null, null, "accessToken이 만료되었습니다.");
        }

        // 블랙 리스트에 포함된 accessToken 인지 확인
        if (logoutTokenRepository.existsByAccessToken(accessToken)) {
            throw new GlobalCustomException(ErrorCode.ACCESS_TOKEN_IS_BLACKLISTED);
        }

        setAuthentication(accessToken);
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String accessToken) {
        String email = jwtTokenUtils.getEmail(accessToken);
        PrincipalDetails principalDetails = principalDetailsService.loadUserByUsername(email);
        Authentication authentication = jwtTokenUtils.getAuthentication(principalDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("현재 사용자 : {}", SecurityContextHolder.getContext().getAuthentication().getName());
        log.info("이메일 : {}", email);
        log.info("권한 : {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
    }
}


