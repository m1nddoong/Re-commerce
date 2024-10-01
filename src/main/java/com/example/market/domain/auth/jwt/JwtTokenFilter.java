package com.example.market.domain.auth.jwt;

import com.example.market.domain.auth.dto.PrincipalDetails;
import com.example.market.domain.auth.service.PrincipalDetailsService;
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

        // TODO: 블랙 리스트에 포함된 accessToken 인지 확인하는 로직 필요
        // 만약 블랙리스트에 등록된 토큰인 경우 예외 처리하기

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


