package com.example.market.auth.jwt;

import com.example.market.auth.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenUtils jwtTokenUtils;
    // 사용자 정보를 찾기위한 UserDetailsService 또는 Manager
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        log.debug("try jwt filter");
        // 1. Authorization 헤더를 회수
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        // 2. Authorization 헤더가 존재하는지 + Bearer로 시작하는지
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.split(" ")[1];
            // 3. Token이 유효한 토큰인지
            if (jwtTokenUtils.validate(token)) {
                // 4. 유효하다면 해당 토큰을 바탕으로 사용자 정보를 SecurityContext에 등록
                String userUUID = jwtTokenUtils
                        .parseClaims(token)
                        .getSubject();
                // 원래는 username으로 DB에서 사용자를 조회하여 userDetails 객체에 저장하는 것 -> uuid 로 변경
                UserDetails userDetails = userService.loadUserByUsername(userUUID);

                SecurityContext context = SecurityContextHolder.createEmptyContext();

                // 인증 정보 생성
                AbstractAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                token,
                                userDetails.getAuthorities()
                        );
                // 인증 정보 등록
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
                log.info("set security context with jwt");
                log.info("인증된 사용자 : {}", SecurityContextHolder.getContext().getAuthentication().getName());
                log.info("권한 상태 : {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());

            } else {
                log.warn("jwt validation failed");
            }
        }
        // 5. 다음 필터 호출
        // doFilter를 호출하지 않으면 Controller까지 요청이 도달하지 못한다.
        filterChain.doFilter(request, response);
    }
}


