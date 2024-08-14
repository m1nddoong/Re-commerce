package com.example.market.common.util;


import com.example.market.auth.constant.BusinessStatus;
import com.example.market.auth.entity.User;
import com.example.market.auth.repo.UserRepository;
import com.example.market.shop.repo.ShopRepository;
import com.example.market.trade.repo.TradeItemRepository;
import com.example.market.trade.repo.TradeOfferRepository;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
@RequiredArgsConstructor
public class UserInitializer implements ApplicationRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        // 관리자
        userRepository.save(User.builder()
                .uuid(UUID.randomUUID())
                .email("admin@naver.com")
                .password(passwordEncoder.encode("1111"))
                .username("김태호")
                .nickname("PD")
                .age(26)
                .phone("010-3637-6533")
                .profileImg(null)
                .businessNum("605-46-5452")
                .businessStatus(BusinessStatus.APPROVED)
                .authorities("ROLE_ACTIVE,ROLE_OWNER,ROLE_ADMIN")
                .build());

        // 사업자1
        userRepository.save(User.builder()
                .uuid(UUID.randomUUID())
                .email("jaeseok@naver.com")
                .password(passwordEncoder.encode("1111"))
                .username("유재석")
                .nickname("MC")
                .age(40)
                .phone("010-7878-1313")
                .profileImg(null)
                .businessNum("603-46-2424")
                .businessStatus(BusinessStatus.APPROVED)
                .authorities("ROLE_ACTIVE,ROLE_OWNER")
                .build());

        // 사업자2
        userRepository.save(User.builder()
                .uuid(UUID.randomUUID())
                .email("myeongsu@naver.com")
                .password(passwordEncoder.encode("1111"))
                .username("박명수")
                .nickname("명수옹")
                .age(37)
                .phone("010-3535-0909")
                .profileImg(null)
                .businessNum("623-46-2424")
                .businessStatus(BusinessStatus.APPROVED)
                .authorities("ROLE_ACTIVE,ROLE_OWNER")
                .build());

        // 사업자3
        userRepository.save(User.builder()
                .uuid(UUID.randomUUID())
                .email("junha@naver.com")
                .password(passwordEncoder.encode("1111"))
                .username("정준하")
                .nickname("주나")
                .age(37)
                .phone("010-3535-0909")
                .profileImg(null)
                .businessNum("533-24-4699")
                .businessStatus(BusinessStatus.APPROVED)
                .authorities("ROLE_ACTIVE,ROLE_OWNER")
                .build());

        // 사업자4
        userRepository.save(User.builder()
                .uuid(UUID.randomUUID())
                .email("hyeongdon@naver.com")
                .password(passwordEncoder.encode("1111"))
                .username("정형돈")
                .nickname("항돈")
                .age(37)
                .phone("010-9393-1616")
                .profileImg(null)
                .businessNum("537-22-3885")
                .businessStatus(BusinessStatus.APPROVED)
                .authorities("ROLE_ACTIVE,ROLE_OWNER")
                .build());

        // 사업자5
        userRepository.save(User.builder()
                .uuid(UUID.randomUUID())
                .email("gil@naver.com")
                .password(passwordEncoder.encode("1111"))
                .username("길")
                .nickname("이간길")
                .age(37)
                .phone("010-6693-8876")
                .profileImg(null)
                .businessNum("237-12-2335")
                .businessStatus(BusinessStatus.APPROVED)
                .authorities("ROLE_ACTIVE,ROLE_OWNER")
                .build());

        // 사업자6
        userRepository.save(User.builder()
                .uuid(UUID.randomUUID())
                .email("hongchul@naver.com")
                .password(passwordEncoder.encode("1111"))
                .username("노홍철")
                .nickname("돌I")
                .age(37)
                .phone("010-6862-1167")
                .profileImg(null)
                .businessNum("787-90-3545")
                .businessStatus(BusinessStatus.APPROVED)
                .authorities("ROLE_ACTIVE,ROLE_OWNER")
                .build());

        // 사업자7
        userRepository.save(User.builder()
                .uuid(UUID.randomUUID())
                .email("haha@naver.com")
                .password(passwordEncoder.encode("1111"))
                .username("하하")
                .nickname("꼬마")
                .age(37)
                .phone("010-9183-1645")
                .profileImg(null)
                .businessNum("545-05-4085")
                .businessStatus(BusinessStatus.APPROVED)
                .authorities("ROLE_ACTIVE,ROLE_OWNER")
                .build());
    }
}
