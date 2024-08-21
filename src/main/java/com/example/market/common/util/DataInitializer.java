package com.example.market.common.util;


import com.example.market.auth.constant.BusinessStatus;
import com.example.market.auth.entity.User;
import com.example.market.auth.repo.UserRepository;
import com.example.market.shop.constant.ItemCategory;
import com.example.market.shop.constant.ItemSubCategory;
import com.example.market.shop.constant.ShopCategory;
import com.example.market.shop.constant.ShopStatus;
import com.example.market.shop.entity.Item;
import com.example.market.shop.entity.Shop;
import com.example.market.shop.repo.ItemRepository;
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
    private final ItemRepository itemRepository;
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
                createShop("정준하 마켓", "뷰티 쇼핑몰", ShopCategory.BEAUTY, ShopStatus.OPEN, users[3]),
                createShop("정형돈 마켓", "가정용품 쇼핑몰", ShopCategory.HOME, ShopStatus.OPEN_REQUEST, users[4]),
                createShop("길의 가게", "스포츠 쇼핑몰", ShopCategory.SPORT, ShopStatus.CLOSE_REQUEST, users[5])
        };
        shopRepository.saveAll(Arrays.asList(shops));

        Item[] items = {
                createShopItem("무한도전 맨투맨1", "남성 맨투맨1 입니다.", 15000, ItemCategory.MENS_CLOTHING, ItemSubCategory.MENS_TOPS, 15, shops[0]),
                createShopItem("무한도전 맨투맨2", "남성 맨투맨2 입니다.", 35000, ItemCategory.MENS_CLOTHING, ItemSubCategory.MENS_TOPS, 15, shops[0]),
                createShopItem("무한도전 맨투맨3", "남성 맨투맨3 입니다.", 14000, ItemCategory.MENS_CLOTHING, ItemSubCategory.MENS_TOPS, 15, shops[0]),
                createShopItem("무한도전 맨투맨4", "남성 맨투맨4 입니다.", 34000, ItemCategory.MENS_CLOTHING, ItemSubCategory.MENS_TOPS, 15, shops[0]),
                createShopItem("무한도전 맨투맨5", "남성 맨투맨5 입니다.", 13000, ItemCategory.MENS_CLOTHING, ItemSubCategory.MENS_TOPS, 15, shops[0]),
                createShopItem("무한도전 맨투맨6", "여성 맨투맨6 입니다.", 33000, ItemCategory.WOMENS_CLOTHING, ItemSubCategory.WOMENS_TOPS, 15, shops[0]),
                createShopItem("무한도전 맨투맨7", "여성 맨투맨7 입니다.", 12000, ItemCategory.WOMENS_CLOTHING, ItemSubCategory.WOMENS_TOPS, 15, shops[0]),
                createShopItem("무한도전 맨투맨8", "여성 맨투맨8 입니다.", 32000, ItemCategory.WOMENS_CLOTHING, ItemSubCategory.WOMENS_TOPS, 15, shops[0]),
                createShopItem("무한도전 맨투맨9", "여성 맨투맨9 입니다.", 11000, ItemCategory.WOMENS_CLOTHING, ItemSubCategory.WOMENS_TOPS, 15, shops[0]),
                createShopItem("무한도전 맨투맨10", "여성 맨투맨10 입니다.", 31000, ItemCategory.WOMENS_CLOTHING, ItemSubCategory.WOMENS_TOPS, 15, shops[0]),
                createShopItem("무한도전 와이드팬츠1", "남성 와이드팬츠1 입니다.", 15000, ItemCategory.MENS_CLOTHING, ItemSubCategory.MENS_BOTTOMS, 10, shops[0]),
                createShopItem("무한도전 와이드팬츠2", "남성 와이드팬츠2 입니다.", 35000, ItemCategory.MENS_CLOTHING, ItemSubCategory.MENS_BOTTOMS, 10, shops[0]),
                createShopItem("무한도전 와이드팬츠3", "남성 와이드팬츠3 입니다.", 14000, ItemCategory.MENS_CLOTHING, ItemSubCategory.MENS_BOTTOMS, 10, shops[0]),
                createShopItem("무한도전 와이드팬츠4", "남성 와이드팬츠4 입니다.", 34000, ItemCategory.MENS_CLOTHING, ItemSubCategory.MENS_BOTTOMS, 10, shops[0]),
                createShopItem("무한도전 와이드팬츠5", "남성 와이드팬츠5 입니다.", 13000, ItemCategory.MENS_CLOTHING, ItemSubCategory.MENS_BOTTOMS, 10, shops[0]),
                createShopItem("무한도전 와이드팬츠6", "여성 와이드팬츠6 입니다.", 33000, ItemCategory.WOMENS_CLOTHING, ItemSubCategory.WOMENS_BOTTOMS, 10, shops[0]),
                createShopItem("무한도전 와이드팬츠7", "여성 와이드팬츠7 입니다.", 12000, ItemCategory.WOMENS_CLOTHING, ItemSubCategory.WOMENS_BOTTOMS, 10, shops[0]),
                createShopItem("무한도전 와이드팬츠8", "여성 와이드팬츠8 입니다.", 32000, ItemCategory.WOMENS_CLOTHING, ItemSubCategory.WOMENS_BOTTOMS, 10, shops[0]),
                createShopItem("무한도전 와이드팬츠9", "여성 와이드팬츠9 입니다.", 11000, ItemCategory.WOMENS_CLOTHING, ItemSubCategory.WOMENS_BOTTOMS, 10, shops[0]),
                createShopItem("무한도전 와이드팬츠10", "여성 와이드팬츠10 입니다.", 31000, ItemCategory.WOMENS_CLOTHING, ItemSubCategory.WOMENS_BOTTOMS, 10, shops[0]),
                createShopItem("벤큐 모니터", "신상 모니터 입니다.", 280000, ItemCategory.ELECTRONIC_COMPUTERS, ItemSubCategory.MONITORS, 7, shops[1]),
                createShopItem("해피해킹 키보드", "신상 키보드 입니다.", 350000, ItemCategory.ELECTRONIC_COMPUTERS, ItemSubCategory.KEYBOARDS, 20, shops[1]),
                createShopItem("로션", "신상 로션 입니다.", 13000, ItemCategory.BEAUTY_SKINCARE, ItemSubCategory.SKINCARE_CREAM, 15, shops[2]),
                createShopItem("샴푸", "신상 샴푸 입니다.", 15000, ItemCategory.BEAUTY_HAIRCARE, ItemSubCategory.HAIR_SHAMPOO, 15, shops[2])
        };
        itemRepository.saveAll(Arrays.asList(items));

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

    private Shop createShop(String name, String introduction, ShopCategory shopCategory, ShopStatus status, User user) {
        return Shop.builder()
                .name(name)
                .introduction(introduction)
                .shopCategory(shopCategory)
                .status(status)
                .user(user)
                .build();
    }

    private Item createShopItem(String name, String description, Integer price, ItemCategory itemCategory,
                                ItemSubCategory itemSubCategory, Integer stock, Shop shop) {
        return Item.builder()
                .name(name)
                .img("example-img.png")
                .description(description)
                .price(price)
                .itemCategory(itemCategory)
                .itemSubCategory(itemSubCategory)
                .stock(stock)
                .shop(shop)
                .build();
    }
}
