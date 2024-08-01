package com.example.market.auth.config;

import com.example.market.auth.jwt.JwtTokenFilter;
import com.example.market.auth.jwt.JwtTokenUtils;
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

    // 메서드의 결과를 Bean 객체로 관리해주는 어노테이션
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/token/**"
                        )
                        .permitAll()
                        .requestMatchers(
                                "/users/my-profile",
                                "/users/update-profile-info",
                                "/users/update-profile-img"
                        )
                        .authenticated()
                        .requestMatchers(
                                "/users/sign-up",
                                "/users/sign-in"
                        )
                        .anonymous()
                        .requestMatchers(
                                "/users/business-application",
                                "/trade-item/**"
                        )
                        .hasRole("ACTIVE")
                        .requestMatchers("/admin/**")
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
