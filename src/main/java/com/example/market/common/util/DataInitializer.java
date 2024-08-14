package com.example.market.common.util;


import com.example.market.auth.constant.BusinessStatus;
import com.example.market.auth.entity.User;
import com.example.market.auth.repo.UserRepository;
import com.example.market.shop.constant.ShopCategory;
import com.example.market.shop.constant.ShopStatus;
import com.example.market.shop.entity.Shop;
import com.example.market.shop.repo.ShopRepository;
import jakarta.transaction.Transactional;
import java.util.Arrays;
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
public class DataInitializer implements ApplicationRunner {
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        User[] users = {
                createUser("admin@naver.com", "김태호", "PD", "605-46-5452", BusinessStatus.APPROVED,
                        "ROLE_ACTIVE,ROLE_OWNER,ROLE_ADMIN"),
                createUser("jaeseok@naver.com", "유재석", "MC", "603-46-2424", BusinessStatus.APPROVED,
                        "ROLE_ACTIVE,ROLE_OWNER"),
                createUser("myeongsu@naver.com", "박명수", "명수옹", "623-46-2424", BusinessStatus.APPROVED,
                        "ROLE_ACTIVE,ROLE_OWNER"),
                createUser("junha@naver.com", "정준하", "주나", "533-24-4699", BusinessStatus.APPROVED,
                        "ROLE_ACTIVE,ROLE_OWNER"),
                createUser("hyeongdon@naver.com", "정형돈", "항돈", "537-22-3885", BusinessStatus.APPROVED,
                        "ROLE_ACTIVE,ROLE_OWNER"),
                createUser("gil@naver.com", "길", "이간길", "237-12-2335", BusinessStatus.APPROVED,
                        "ROLE_ACTIVE,ROLE_OWNER"),
                createUser("hongchul@naver.com", "노홍철", "돌I", "787-90-3545", BusinessStatus.APPROVED,
                        "ROLE_ACTIVE,ROLE_OWNER"),
                createUser("haha@naver.com", "하하", "꼬마", "545-05-4085", BusinessStatus.APPROVED,
                        "ROLE_ACTIVE,ROLE_OWNER")
        };
        userRepository.saveAll(Arrays.asList(users));

        Shop[] shops = {
                createShop("유재석의 가게", "패션 쇼핑몰", ShopCategory.FASHION, ShopStatus.OPEN, users[1]),
                createShop("박명수의 가게", "전자기기 쇼핑몰", ShopCategory.ELECTRONICS, ShopStatus.OPEN, users[2]),
                createShop("정준하의 가게", "뷰티 쇼핑몰", ShopCategory.BEAUTY, ShopStatus.OPEN, users[3]),
                createShop("정형돈의 가게", "가정용품 쇼핑몰", ShopCategory.HOME, ShopStatus.OPEN, users[4]),
                createShop("길의 가게", "스포츠 쇼핑몰", ShopCategory.SPORT, ShopStatus.OPEN, users[5])
        };
        shopRepository.saveAll(Arrays.asList(shops));
    }

    private User createUser(String email, String username, String nickname, String businessNum,
                            BusinessStatus businessStatus, String authorities) {
        return User.builder()
                .uuid(UUID.randomUUID())
                .email(email)
                .password(passwordEncoder.encode("1111"))
                .username(username)
                .nickname(nickname)
                .age(37)  // 나이를 임시로 동일하게 설정
                .phone("010-0000-0000")  // 전화번호를 임시로 설정
                .profileImg(null)
                .businessNum(businessNum)
                .businessStatus(businessStatus)
                .authorities(authorities)
                .build();
    }

    private Shop createShop(String name, String introduction, ShopCategory category, ShopStatus status, User user) {
        return Shop.builder()
                .name(name)
                .introduction(introduction)
                .category(category)
                .status(status)
                .user(user)
                .build();
    }
}
