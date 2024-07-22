package com.example.market.auth.config;

import lombok.RequiredArgsConstructor;
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
                // form 에서 보내지는 post 요청을 시큐리티가 막는것을 방지하기 위해 추가
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        // no-auth 로 오는 요청은 모두 허가
                        auth -> auth
                                .requestMatchers(
                                        "/users/my-profile",
                                        "/users/home"
                                        )
                                .authenticated()
                                .requestMatchers("/users/register")
                                .anonymous()
                )
                // html form 요소를 이용해 로그인을 시키는 설정
                .formLogin(
                        formLogin -> formLogin
                                // 어떤 경로로 요청을 보내면 로그인 페이지가 나오는지
                                .loginPage("/users/login")
                                // 아무 설정 없이 로그인에 성공한 뒤 이동할 URL
                                .defaultSuccessUrl("/users/my-profile")
                                // 로그인에 실패시 이동할 URL
                                .failureUrl("/users/login?fail")
                                .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/users/logout")
                        .logoutSuccessUrl("/users/login")
                )
        ;

        return http.build();
    }


    // 비밀번호 암호화 클래스
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
