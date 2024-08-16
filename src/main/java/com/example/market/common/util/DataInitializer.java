package com.example.market.common.util;


import com.example.market.auth.constant.BusinessStatus;
import com.example.market.auth.entity.User;
import com.example.market.auth.repo.UserRepository;
import com.example.market.shop.constant.ItemCategory;
import com.example.market.shop.constant.ItemSubCategory;
import com.example.market.shop.constant.ShopCategory;
import com.example.market.shop.constant.ShopStatus;
import com.example.market.shop.entity.Shop;
import com.example.market.shop.entity.ShopItem;
import com.example.market.shop.repo.ShopItemRepository;
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
    private final ShopItemRepository shopItemRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        User[] users = {
                createUser("admin@naver.com", "김태호", "PD", "605-46-5452",
                        "ROLE_ACTIVE,ROLE_OWNER,ROLE_ADMIN"),
                createUser("jaeseok@naver.com", "유재석", "MC", "603-46-2424",
                        "ROLE_ACTIVE,ROLE_OWNER"),
                createUser("myeongsu@naver.com", "박명수", "명수옹", "623-46-2424",
                        "ROLE_ACTIVE,ROLE_OWNER"),
                createUser("junha@naver.com", "정준하", "주나", "533-24-4699",
                        "ROLE_ACTIVE,ROLE_OWNER"),
                createUser("hyeongdon@naver.com", "정형돈", "항돈", "537-22-3885",
                        "ROLE_ACTIVE,ROLE_OWNER"),
                createUser("gil@naver.com", "길", "이간길", "237-12-2335",
                        "ROLE_ACTIVE,ROLE_OWNER"),
                createUser("hongchul@naver.com", "노홍철", "돌I", "787-90-3545",
                        "ROLE_ACTIVE,ROLE_OWNER"),
                createUser("haha@naver.com", "하하", "꼬마", "545-05-4085",
                        "ROLE_ACTIVE,ROLE_OWNER")
        };
        userRepository.saveAll(Arrays.asList(users));

        Shop[] shops = {
                createShop("유재석의 가게", "패션 쇼핑몰", ShopCategory.FASHION, ShopStatus.OPEN, users[1]),
                createShop("박명수의 가게", "전자기기 쇼핑몰", ShopCategory.ELECTRONICS, ShopStatus.OPEN, users[2]),
                createShop("정준하의 가게", "뷰티 쇼핑몰", ShopCategory.BEAUTY, ShopStatus.OPEN, users[3]),
                createShop("정형돈의 가게", "가정용품 쇼핑몰", ShopCategory.HOME, ShopStatus.OPEN_REQUEST, users[4]),
                createShop("길의 가게", "스포츠 쇼핑몰", ShopCategory.SPORT, ShopStatus.CLOSE_REQUEST, users[5])
        };
        shopRepository.saveAll(Arrays.asList(shops));

        ShopItem[] shopItems = {
                createShopItem("맨두맨", "신상 맨투맨 입니다.", "35000", ItemCategory.MENS_CLOTHING, ItemSubCategory.MENS_TOPS, 15, shops[0]),
                createShopItem("코트", "신상 코트 입니다.", "120000", ItemCategory.MENS_CLOTHING, ItemSubCategory.MENS_TOPS, 10, shops[0]),
                createShopItem("벤큐 모니터", "신상 모니터 입니다.", "280000", ItemCategory.ELECTRONIC_COMPUTERS, ItemSubCategory.MONITORS, 7, shops[1]),
                createShopItem("해피해킹 키보드", "신상 키보드 입니다.", "350000", ItemCategory.ELECTRONIC_COMPUTERS, ItemSubCategory.KEYBOARDS, 20, shops[1]),
                createShopItem("로션", "신상 로션 입니다.", "13000", ItemCategory.BEAUTY_SKINCARE, ItemSubCategory.SKINCARE_CREAM, 15, shops[2]),
                createShopItem("샴푸", "신상 샴푸 입니다.", "15000", ItemCategory.BEAUTY_HAIRCARE, ItemSubCategory.HAIR_SHAMPOO, 15, shops[2])
        };
        shopItemRepository.saveAll(Arrays.asList(shopItems));

    }

    private User createUser(String email, String username, String nickname, String businessNum,
                            String authorities) {
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
                .businessStatus(BusinessStatus.APPROVED)
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

    private ShopItem createShopItem(String name, String description, String price, ItemCategory category,
                                    ItemSubCategory subCategory, Integer stock, Shop shop) {
        return ShopItem.builder()
                .name(name)
                .img("example-img.png")
                .description(description)
                .price(price)
                .category(category)
                .subCategory(subCategory)
                .stock(stock)
                .shop(shop)
                .build();
    }
}
