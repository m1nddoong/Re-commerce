package com.example.market.global.config;

import com.example.market.domain.auth.service.PrincipalDetailsService;
import com.example.market.domain.auth.jwt.JwtTokenFilter;
import com.example.market.domain.auth.jwt.JwtTokenUtils;
import com.example.market.domain.auth.handler.OAuth2LoginSuccessHandler;
import com.example.market.domain.auth.service.PrincipalOAuth2UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

// Bean을 비롯해서 여러 설정을 하기 위한 Bean 객체
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenUtils jwtTokenUtils;
    private final PrincipalDetailsService principalDetailsService;
    private final PrincipalOAuth2UserService principalOAuth2UserService;
    private final OAuth2LoginSuccessHandler OAuth2LoginSuccessHandler;

    // 메서드의 결과를 Bean 객체로 관리해주는 어노테이션
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration configuration = new CorsConfiguration();
                        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);
                        configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));
                        return configuration;
                    }
                }))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint((userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(principalOAuth2UserService)))
                        .successHandler(OAuth2LoginSuccessHandler)) // 동의하고 계속하기 눌렀을 때 Handler 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v1/api-docs/**",
                                "/v1/api-docs",
                                "/",
                                "/login/success",
                                "/jwt/verify",
                                "/api/v1/auth/sign-up",
                                "/api/v1/auth/sign-in"
                        )
                        .permitAll()
                        .requestMatchers(
                                "/api/v1/auth/sign-out",
                                "/api/v1/auth/profile",
                                "/api/v1/auth/profile/update",
                                "/api/v1/auth/token/refresh"
                        )
                        .authenticated()
                        .requestMatchers(
                                "/api/v1/auth/business-apply",
                                "/api/v1/trade-item/**",
                                "/api/v1/trade-offers/**",
                                "/api/v1/shop/search",
                                "/api/v1/shop/update",
                                "/api/v1/shop/open-request",
                                "/api/v1/shop/close-request",
                                "/api/v1/item/search/**",
                                "/api/v1/item/categories",
                                "/api/v1/item/categories/{categoryId}",
                                "/api/v1/order/create",
                                "/api/v1/order/{orderId}/cancel"
                        )
                        .hasRole("ACTIVE")
                        .requestMatchers(
                                "/api/v1/item/create",
                                "/api/v1/item/update/{shopItemId}",
                                "/api/v1/item/delete/{shopItemId}",
                                "/api/v1/item/sale"
                        )
                        .hasRole("OWNER")
                        .requestMatchers(
                                "/api/v1/auth/business-requests/**",
                                "/api/v1/shop/open-request/list",
                                "/api/v1/shop/open-request/{shopId}/approve",
                                "/api/v1/shop/open-request/{shopId}/reject",
                                "/api/v1/shop/close-request/list",
                                "/api/v1/shop/close-request/{shopId}/approve",
                                "/api/v1/item/categories/merge/**",
                                "/api/v1/item/categories/merge/sub/**",
                                "/api/v1/order/{orderId}/approve"
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
                                principalDetailsService
                        ),
                        UsernamePasswordAuthenticationFilter.class
                )
        ;
        return http.build();

    }
}
