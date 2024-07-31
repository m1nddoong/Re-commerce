package com.example.market.auth.util;

import com.example.market.auth.entity.User;
import com.example.market.auth.repo.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.DeclareWarning;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements ApplicationRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 관리자 계정
        userRepository.save(User.builder()
                .uuid(UUID.randomUUID())
                .email("admin123@naver.com")
                .password(passwordEncoder.encode("1111"))
                .username("유재석")
                .nickname("관리자")
                .age(26)
                .phone("010-9292-5757")
                .profileImg(null)
                .businessNum("605-46-5452")
                .businessApply(true)
                .authorities("ROLE_ACTIVE,ROLE_OWNER,ROLE_ADMIN")
                .build());

        // 사업자 사용자
        userRepository.save(User.builder()
                .uuid(UUID.randomUUID())
                .email("owner123@naver.com")
                .password(passwordEncoder.encode("2222"))
                .username("박명수")
                .nickname("사업자 사용자")
                .age(40)
                .phone("010-7878-1313")
                .profileImg(null)
                .businessNum("603-46-2424")
                .businessApply(true)
                .authorities("ROLE_ACTIVE,ROLE_OWNER")
                .build());
    }
}
