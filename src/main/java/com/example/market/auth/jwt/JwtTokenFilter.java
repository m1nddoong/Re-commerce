package com.example.market.auth.jwt;

import com.example.market.auth.entity.RefreshToken;
import com.example.market.auth.entity.User;
import com.example.market.auth.repo.RefreshTokenRepository;
import com.example.market.auth.repo.UserRepository;
import com.example.market.auth.service.UserService;
import com.example.market.common.exception.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Lettuce.Cluster.Refresh;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenUtils jwtTokenUtils;
    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        log.debug("try jwt filter");
        // 1. Authorization 헤더를 회수하고, 헤더가 존재하고 Bearer로 시작하는지
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // 2. Access Token이 유효하다면 해당 토큰을 바탕으로 사용자 정보를 SecurityContext에 등록
            String accessToken = authHeader.split(" ")[1];
            if (jwtTokenUtils.validate(accessToken)) {
                String userUUID = jwtTokenUtils
                        .parseClaims(accessToken)
                        .getSubject();
                // 원래는 username으로 DB에서 사용자를 조회하여 userDetails 객체에 저장 -> uuid 로 조회
                UserDetails userDetails = userService.loadUserByUsername(userUUID);
                // 3. 시큐리티 컨텍스트 홀더 생성, 인증 정보 생성 및 등록
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                AbstractAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                accessToken,
                                userDetails.getAuthorities()
                        );
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
                log.info("set security context with jwt");
                log.info("인증된 사용자 : {}", SecurityContextHolder.getContext().getAuthentication().getName());
                log.info("권한 상태 : {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            }
            // 4. accessToken 이 유효하지 않다면
            else {
                log.warn("jwt validation failed");
                // 만료된 access token을 가지고 redis 에 저장되어 있는 토큰 정보를 가져온다.
                RefreshToken foundTokenInfo = refreshTokenRepository.findByAccessToken(accessToken)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
                // 가져온 토큰 정보에서 refreshToken 정보를 받아오고
                String refreshToken = foundTokenInfo.getRefreshToken();
                // 받아온 refreshToken 가 유효하다면
                if (jwtTokenUtils.validate(refreshToken)) {
                    // redis 에 저장된 토큰 정보의 uuid 정보를 가지고 사용자 정보를 가져온다.
                    String uuid = foundTokenInfo.getUserUuid();
                    User foundUser = userRepository.findUserByUuid(UUID.fromString(uuid))
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

                    // 사용자 정보로 다시 Access Token 을 만들어 발급한 뒤 refreshToken 과 함꼐 redis 업데이트
                    accessToken = jwtTokenUtils.generateToken(foundUser, TokenType.ACCESS);
                    refreshTokenRepository.save(new RefreshToken(uuid, refreshToken, accessToken));
                }
                // 탈취범에 의해 refreshToken 이 탈취되어 내 refreshToken 마저도 변경된 상태
                // 로그아웃이 되더라도 redis 에 남아있는 탈취범이 발급한 refreshToken 을 어찌할 방법이 없다.
            }
        }
        // 5. 다음 필터 호출
        // doFilter를 호출하지 않으면 Controller까지 요청이 도달하지 못한다.
        filterChain.doFilter(request, response);
    }
}


