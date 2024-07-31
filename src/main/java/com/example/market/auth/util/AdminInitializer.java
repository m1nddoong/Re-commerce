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
                .username("송승현")
                .nickname("관리자")
                .age(26)
                .phone("010-3637-6533")
                .profileImg(null)
                .businessNum("605-46-5452")
                .businessApply(false)
                .authorities("ROLE_ACTIVE,ROLE_OWNER,ROLE_ADMIN")
                .build());

        // 사업자 사용자
        userRepository.save(User.builder()
                .uuid(UUID.randomUUID())
                .email("owner123@naver.com")
                .password(passwordEncoder.encode("2222"))
                .username("유재석")
                .nickname("사업자 사용자")
                .age(40)
                .phone("010-7878-1313")
                .profileImg(null)
                .businessNum("603-46-2424")
                .businessApply(false)
                .authorities("ROLE_ACTIVE,ROLE_OWNER")
                .build());

        // 일반 사용자 1
        userRepository.save(User.builder()
                .uuid(UUID.randomUUID())
                .email("user1@naver.com")
                .password(passwordEncoder.encode("3333"))
                .username("박명수")
                .nickname("일반 사용자")
                .age(37)
                .phone("010-3535-0909")
                .profileImg(null)
                .businessNum("623-46-2424")
                .businessApply(true)
                .authorities("ROLE_ACTIVE")
                .build());

        // 일반 사용자 2
        userRepository.save(User.builder()
                .uuid(UUID.randomUUID())
                .email("user1@naver.com")
                .password(passwordEncoder.encode("4444"))
                .username("정준하")
                .nickname("일반 사용자")
                .age(37)
                .phone("010-3535-0909")
                .profileImg(null)
                .businessNum(null)
                .businessApply(true)
                .authorities("ROLE_ACTIVE")
                .build());
    }
}
