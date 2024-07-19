package com.example.market.common.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// Bean을 비롯해서 여러 설정을 하기 위한 Bean 객체
@Configuration
public class WebSecurityConfig {

    // 메서드의 결과를 Bean 객체로 관리해주는 어노테이션
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        // no-auth 로 오는 요청은 모두 허가
                        auth -> auth
                                // 어떤 경로에 대한 설정인지
                                .requestMatchers("/no-auth")
                                // 이 경로에 도달할 수 있는 사람에 대한 설정 (모두)
                                .permitAll()
                                .requestMatchers("/users/my-profile")
                                .authenticated()

                )
        ;

        return http.build();
    }


    @Bean
    public UserDetailsManager userDetailsManager() {
        UserDetails user1 = User.withUsername("user1")
                .password("password1")
                .build();
        // Spring Security에서 기본으로 제공하는
        // 메모리 기반 사용자 관리 클래스 + 사용자 1
        return new InMemoryUserDetailsManager(user1);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
