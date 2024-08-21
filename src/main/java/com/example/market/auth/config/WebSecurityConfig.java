package com.example.market.auth.config;

import com.example.market.auth.jwt.JwtTokenFilter;
import com.example.market.auth.jwt.JwtTokenUtils;
import com.example.market.auth.repo.RefreshTokenRepository;
import com.example.market.auth.repo.UserRepository;
import com.example.market.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

// Bean을 비롯해서 여러 설정을 하기 위한 Bean 객체
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenUtils jwtTokenUtils;
    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    // 메서드의 결과를 Bean 객체로 관리해주는 어노테이션
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v1/api-docs/*",
                                "/v1/api-docs"
                        )
                        .permitAll()
                        .requestMatchers(
                                "/api/v1/users/my-profile",
                                "/api/v1/users/update-profile-info",
                                "/api/v1/users/update-profile-img",
                                "/api/v1/token/reissue-token"
                        )
                        .authenticated()
                        .requestMatchers(
                                "/api/v1/users/sign-up",
                                "/api/v1/users/sign-in"
                        )
                        .anonymous()
                        .requestMatchers(
                                "/api/v1/users/business-application",
                                "/api/v1/trade-item/**",
                                "/api/v1/trade-offer/**",
                                "/api/v1/shop/search",
                                "/api/v1/shop/update",
                                "/api/v1/shop/open-request",
                                "/api/v1/shop/close-request",
                                "/api/v1/item/search/**",
                                "/api/v1/order/create",
                                "/api/v1/order/cancel/{orderId}"
                        )
                        .hasRole("ACTIVE")
                        .requestMatchers(
                                "/api/v1/item/create",
                                "/api/v1/item/update/{shopItemId}",
                                "/api/v1/item/delete/{shopItemId}"
                        )
                        .hasRole("OWNER")
                        .requestMatchers(
                                "/api/v1/admin/**",
                                "/api/v1/shop/open-request-list",
                                "/api/v1/shop/open-request/{shopId}/approval",
                                "/api/v1/shop/open-request/{shopId}/rejection",
                                "/api/v1/shop/close-request-list",
                                "/api/v1/shop/close-request/{shopId}/approval",
                                "/api/v1/order/approval/{orderId}"
                        )
                        .hasRole("ADMIN")

                )
                // JWT를 사용하기 때문에 보안 관련 세션 해제
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // JWT 필터를 권한 필터 앞에 삽입
                .addFilterBefore(
                        new JwtTokenFilter(
                                jwtTokenUtils,
                                userService
                        ),
                        AuthorizationFilter.class
                )
        ;
        return http.build();

    }
}
