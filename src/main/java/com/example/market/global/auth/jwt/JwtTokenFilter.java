package com.example.market.global.auth.jwt;

import com.example.market.domain.user.dto.UserDto;
import com.example.market.domain.user.service.UserService;
import com.example.market.global.auth.oauth2.dto.PrincipalDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenUtils jwtTokenUtils;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // cookie 들을 불러온 뒤 Authorization Key에 담긴 쿠키를 찾음
        String authorization = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) { //
            for (Cookie cookie : cookies) {
                log.info("cookie name: {}", cookie.getName());
                if (cookie.getName().equals("Authorization")) {
                    authorization = cookie.getValue();
                    log.info("authorization : {}", authorization);
                }
            }
        } else {
            log.info("No cookies found");
        }
        // Authorization 검증
        if (authorization == null) {
            log.info("token null");
            // 다음 필터로 넘기고
            filterChain.doFilter(request, response);
            // 메서드 종료
            return;
        }
        // 토큰
        String token = authorization;

        // 토큰 소멸 시간 검증
        if (jwtTokenUtils.isExpired(token)) {
            log.info("token expired");
            // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
            filterChain.doFilter(request, response);
        }

        // 토큰에서 email 과 role 획득
        String email = jwtTokenUtils.getEmail(token);
        // String role = jwtTokenUtils.getRole(token);


        try {
            // 사용자 정보를 로드
            PrincipalDetails principalDetails = userService.loadUserByUsername(email);

            // Authentication 객체를 생성하고 SecurityContext에 설정
            Authentication authToken = new UsernamePasswordAuthenticationToken(
                    principalDetails, null, principalDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);

            log.info("인증된 사용자 : {}", SecurityContextHolder.getContext().getAuthentication().getName());
            log.info("권한 상태 : {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());

        } catch (UsernameNotFoundException e) {
            log.error("User not found: {}", email);
            // 사용자 정보를 찾을 수 없는 경우
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
            return;
        }
        filterChain.doFilter(request, response);

    }
}







//        log.debug("try jwt filter");
//        // 1. Authorization 헤더를 회수하고, 헤더가 존재하고 Bearer로 시작하는지
//        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            // 2. Access Token이 유효하다면 해당 토큰을 바탕으로 사용자 정보를 SecurityContext에 등록
//            String accessToken = authHeader.split(" ")[1];
//            if (jwtTokenUtils.validate(accessToken)) {
//                String userUUID = jwtTokenUtils
//                        .parseClaims(accessToken)
//                        .getSubject();
//                // 원래는 username으로 DB에서 사용자를 조회하여 userDetails 객체에 저장 -> uuid 로 조회
//                UserDetails userDetails = userService.loadUserByUsername(userUUID);
//                // 3. 시큐리티 컨텍스트 홀더 생성, 인증 정보 생성 및 등록
//                SecurityContext context = SecurityContextHolder.createEmptyContext();
//                AbstractAuthenticationToken authentication =
//                        new UsernamePasswordAuthenticationToken(
//                                userDetails,
//                                accessToken,
//                                userDetails.getAuthorities()
//                        );
//                context.setAuthentication(authentication);
//                SecurityContextHolder.setContext(context);
//                log.info("set security context with jwt");
//                log.info("인증된 사용자 : {}", SecurityContextHolder.getContext().getAuthentication().getName());
//                log.info("권한 상태 : {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
//            }
//            // 4. accessToken 이 유효하지 않다면
//            else {
//                log.warn("jwt validation failed");
//            }
//        }
//        // 5. 다음 필터 호출
//        // doFilter를 호출하지 않으면 Controller까지 요청이 도달하지 못한다.
//        filterChain.doFilter(request, response);

