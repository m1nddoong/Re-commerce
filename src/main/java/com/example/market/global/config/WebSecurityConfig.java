package com.example.market.global.config;

import com.example.market.global.auth.jwt.JwtTokenFilter;
import com.example.market.global.auth.jwt.JwtTokenUtils;
import com.example.market.domain.user.repository.RefreshTokenRepository;
import com.example.market.domain.user.repository.UserRepository;
import com.example.market.domain.user.service.UserService;
import com.example.market.global.auth.oauth2.CustomSuccessHandler;
import com.example.market.global.auth.oauth2.service.CustomOAuth2UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

// Bean을 비롯해서 여러 설정을 하기 위한 Bean 객체
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenUtils jwtTokenUtils;
    private final UserService userService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;

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
                                .userService(customOAuth2UserService)))
                        .successHandler(customSuccessHandler))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v1/api-docs/*",
                                "/v1/api-docs",
                                "/",
                                "/login/success",
                                "/jwt/verify",
                                "/api/v1/users/sign-up",
                                "/api/v1/users/sign-in"
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
                                "/api/v1/users/business-application",
                                "/api/v1/trade-item/**",
                                "/api/v1/trade-offer/**",
                                "/api/v1/shop/search",
                                "/api/v1/shop/update",
                                "/api/v1/shop/open-request",
                                "/api/v1/shop/close-request",
                                "/api/v1/item/search/**",
                                "/api/v1/item/categories",
                                "/api/v1/item/categories/{categoryId}",
                                "/api/v1/order/create",
                                "/api/v1/order/cancel/{orderId}"

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
                                "/api/v1/users/business-application/list",
                                "/api/v1/users/business-application/{uuid}/approval",
                                "/api/v1/users/business-application/{uuid}/rejection",
                                "/api/v1/shop/open-request-list",
                                "/api/v1/shop/open-request/{shopId}/approval",
                                "/api/v1/shop/open-request/{shopId}/rejection",
                                "/api/v1/shop/close-request-list",
                                "/api/v1/shop/close-request/{shopId}/approval",
                                "/api/v1/item/categories/merge/**",
                                "/api/v1/item/categories/merge/sub/**",
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
                        UsernamePasswordAuthenticationFilter.class
                        // AuthorizationFilter.class
                )
        ;
        return http.build();

    }
}
