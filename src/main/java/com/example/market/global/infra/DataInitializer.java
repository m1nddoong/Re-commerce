package com.example.market.global.infra;


import com.example.market.domain.shop.entity.Category;
import com.example.market.domain.auth.constant.BusinessStatus;
import com.example.market.domain.auth.constant.Role;
import com.example.market.domain.auth.entity.User;
import com.example.market.domain.auth.repository.UserRepository;

import com.example.market.domain.shop.entity.SubCategory;
import com.example.market.domain.shop.constant.ShopCategory;
import com.example.market.domain.shop.constant.ShopStatus;
import com.example.market.domain.shop.entity.Item;
import com.example.market.domain.shop.entity.Shop;
import com.example.market.domain.shop.repository.CategoryRepository;
import com.example.market.domain.shop.repository.ItemRepository;
import com.example.market.domain.shop.repository.SubCategoryRepository;
import com.example.market.domain.shop.repository.ShopRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        User[] users = {
                createUser("admin@naver.com", "김태호", "PD", "605-46-5452",
                        Role.ACTIVE_USER),
                createUser("jaeseok@naver.com", "유재석", "국민 MC", "603-46-2424",
                        Role.BUSINESS_USER),
                createUser("myeongsu@naver.com", "박명수", "박거성", "623-46-2424",
                        Role.BUSINESS_USER),
                createUser("junha@naver.com", "정준하", "주나", "533-24-4699",
                        Role.BUSINESS_USER),
                createUser("hyeongdon@naver.com", "정형돈", "항돈", "537-22-3885",
                        Role.BUSINESS_USER),
                createUser("gil@naver.com", "길", "빡빡이", "237-12-2335",
                        Role.BUSINESS_USER),
                createUser("hongchul@naver.com", "노홍철", "돌I", "787-90-3545",
                        Role.BUSINESS_USER),
                createUser("haha@naver.com", "하하", "꼬마", "545-05-4085",
                        Role.BUSINESS_USER),
                createUser("hodong@naver.com", "강호동", "천하장사", "603-46-2425",
                        Role.BUSINESS_USER),
                createUser("sugeun@naver.com", "이수근", "수그니", "623-46-2426",
                        Role.BUSINESS_USER),
                createUser("jiwon@naver.com", "은지원", "구미", "533-24-4679",
                        Role.BUSINESS_USER),
                createUser("gyuheon@naver.com", "조규현", "조정뱅이", "537-22-3285",
                        Role.BUSINESS_USER),
                createUser("jaehyeon@naver.com", "안재현", "신미", "237-12-2395",
                        Role.BUSINESS_USER),
                createUser("minho@naver.com", "송민호", "송가락", "787-90-1245",
                        Role.BUSINESS_USER),
                createUser("pio@naver.com", "피오", "트럼프", "545-05-4225",
                        Role.BUSINESS_USER)
        };
        userRepository.saveAll(Arrays.asList(users));


        // 상품 분류
        Category[] itemCategories = {
                createItemCategory("남성 의류"),
                createItemCategory("여성 의류"),
                createItemCategory("휴대폰"),
                createItemCategory("컴퓨터"),
                createItemCategory("피부"),
                createItemCategory("스킨"),
                createItemCategory("헤어"),
                createItemCategory("주방"),
                createItemCategory("가구"),
                createItemCategory("스포츠 웨어"),
                createItemCategory("스포츠 용품"),

        };
        categoryRepository.saveAll(Arrays.asList(itemCategories));

        // 상품 소분류
        SubCategory[] itemSubCategories = {
                createItemSubCategory("상의", itemCategories[0]),
                createItemSubCategory("하의", itemCategories[0]),
                createItemSubCategory("악세사리", itemCategories[0]),
                createItemSubCategory("상의", itemCategories[1]),
                createItemSubCategory("하의", itemCategories[1]),
                createItemSubCategory("악세사리", itemCategories[1]),
                createItemSubCategory("삼성", itemCategories[2]),
                createItemSubCategory("애플", itemCategories[2]),
                createItemSubCategory("모니터", itemCategories[3]),
                createItemSubCategory("키보드", itemCategories[3]),
                createItemSubCategory("메이크업", itemCategories[4]),
                createItemSubCategory("보습", itemCategories[4]),
                createItemSubCategory("화장", itemCategories[5]),
                createItemSubCategory("보습", itemCategories[5]),
                createItemSubCategory("헤어 스프레이", itemCategories[6]),
                createItemSubCategory("헤어 보습", itemCategories[6]),
                createItemSubCategory("주방 용품", itemCategories[7]),
                createItemSubCategory("인테리어", itemCategories[8]),
                createItemSubCategory("운동복", itemCategories[9]),
                createItemSubCategory("운동기구", itemCategories[10]),
                createItemSubCategory("보충제", itemCategories[10]),


        };
        subCategoryRepository.saveAll(Arrays.asList(itemSubCategories));

        Shop[] shops = {
                createShop("유재석 마켓", "패션 쇼핑몰", ShopCategory.FASHION, ShopStatus.OPEN, users[1]),
                createShop("박명수 마켓", "패션 쇼핑몰", ShopCategory.FASHION, ShopStatus.OPEN, users[2]),
                createShop("정준하 마켓", "패션 쇼핑몰", ShopCategory.FASHION, ShopStatus.OPEN_REQUEST, users[3]),
                createShop("정형돈 마켓", "뷰티 쇼핑몰", ShopCategory.BEAUTY, ShopStatus.OPEN, users[4]),
                createShop("길 마켓", "뷰티 쇼핑몰", ShopCategory.BEAUTY, ShopStatus.OPEN, users[5]),
                createShop("노홍철 마켓", "뷰티 쇼핑몰", ShopCategory.BEAUTY, ShopStatus.OPEN_REQUEST, users[6]),
                createShop("하하 마켓", "가정용품 쇼핑몰", ShopCategory.HOME, ShopStatus.OPEN, users[7]),
                createShop("강호동 마켓", "가정용품 쇼핑몰", ShopCategory.HOME, ShopStatus.OPEN, users[8]),
                createShop("이수근 마켓", "가정용품 쇼핑몰", ShopCategory.HOME, ShopStatus.CLOSE_REQUEST, users[9]),
                createShop("은지원 마켓", "스포츠 쇼핑몰", ShopCategory.SPORT, ShopStatus.OPEN, users[10]),
                createShop("조규현 마켓", "스포츠 쇼핑몰", ShopCategory.SPORT, ShopStatus.OPEN, users[11]),
                createShop("안재현 마켓", "스포츠 쇼핑몰", ShopCategory.SPORT, ShopStatus.CLOSE_REQUEST, users[12]),
        };
        shopRepository.saveAll(Arrays.asList(shops));

        Item[] items = {
                createShopItem("무한도전 맨투맨1", "남성 맨투맨1 입니다.", 15000, itemCategories[0], itemSubCategories[0], 15, shops[0]),
                createShopItem("무한도전 맨투맨2", "남성 맨투맨2 입니다.", 35000, itemCategories[0], itemSubCategories[0], 15, shops[0]),
                createShopItem("무한도전 맨투맨3", "남성 맨투맨3 입니다.", 14000, itemCategories[0], itemSubCategories[0], 15, shops[0]),
                createShopItem("무한도전 맨투맨4", "남성 맨투맨4 입니다.", 34000, itemCategories[0], itemSubCategories[0], 15, shops[0]),
                createShopItem("무한도전 맨투맨5", "남성 맨투맨5 입니다.", 13000, itemCategories[0], itemSubCategories[0], 15, shops[0]),
                createShopItem("무한도전 맨투맨6", "여성 맨투맨6 입니다.", 33000, itemCategories[1], itemSubCategories[0], 15, shops[0]),
                createShopItem("무한도전 맨투맨7", "여성 맨투맨7 입니다.", 12000, itemCategories[1], itemSubCategories[0], 15, shops[0]),
                createShopItem("무한도전 맨투맨8", "여성 맨투맨8 입니다.", 32000, itemCategories[1], itemSubCategories[0], 15, shops[0]),
                createShopItem("무한도전 맨투맨9", "여성 맨투맨9 입니다.", 11000, itemCategories[1], itemSubCategories[0], 15, shops[0]),
                createShopItem("무한도전 맨투맨10", "여성 맨투맨10 입니다.", 31000, itemCategories[1], itemSubCategories[0], 15, shops[0]),
                createShopItem("무한도전 와이드팬츠1", "남성 와이드팬츠1 입니다.", 15000, itemCategories[0], itemSubCategories[1], 10, shops[0]),
                createShopItem("무한도전 와이드팬츠2", "남성 와이드팬츠2 입니다.", 35000, itemCategories[0], itemSubCategories[1], 10, shops[0]),
                createShopItem("무한도전 와이드팬츠3", "남성 와이드팬츠3 입니다.", 14000, itemCategories[0], itemSubCategories[1], 10, shops[0]),
                createShopItem("무한도전 와이드팬츠4", "남성 와이드팬츠4 입니다.", 34000, itemCategories[0], itemSubCategories[1], 10, shops[0]),
                createShopItem("무한도전 와이드팬츠5", "남성 와이드팬츠5 입니다.", 13000, itemCategories[0], itemSubCategories[1], 10, shops[0]),
                createShopItem("무한도전 와이드팬츠6", "여성 와이드팬츠6 입니다.", 33000, itemCategories[1], itemSubCategories[1], 10, shops[0]),
                createShopItem("무한도전 와이드팬츠7", "여성 와이드팬츠7 입니다.", 12000, itemCategories[1], itemSubCategories[1], 10, shops[0]),
                createShopItem("무한도전 와이드팬츠8", "여성 와이드팬츠8 입니다.", 32000, itemCategories[1], itemSubCategories[1], 10, shops[0]),
                createShopItem("무한도전 와이드팬츠9", "여성 와이드팬츠9 입니다.", 11000, itemCategories[1], itemSubCategories[1], 10, shops[0]),
                createShopItem("무한도전 와이드팬츠10", "여성 와이드팬츠10 입니다.", 31000, itemCategories[1], itemSubCategories[1], 10, shops[0]),
                // 통합할 두 카테고리와 서브카테고리
                createShopItem("피부 메이크업 용품1", "피부 메이크업 용품1 입니다.", 10000, itemCategories[4], itemSubCategories[10], 10, shops[4]),
                createShopItem("피부 보습 용품1", "피부 보습 용품1 입니다.", 12000, itemCategories[4], itemSubCategories[11], 10, shops[4]),
                createShopItem("스킨 화장 용품1", "스킨 화장 용품1 입니다.", 10000, itemCategories[5], itemSubCategories[12], 10, shops[5]),
                createShopItem("스킨 보습 용품1", "스킨 보습 용품1 입니다.", 12000, itemCategories[5], itemSubCategories[13], 10, shops[5])



        };
        itemRepository.saveAll(Arrays.asList(items));
    }

    private User createUser(String email, String username, String nickname, String businessNum,
                            Role role) {
        return User.builder()
                .uuid(UUID.randomUUID())
                .email(email)
                .password(passwordEncoder.encode("1111"))
                .username(username)
                .nickname(nickname)
                .birthday(LocalDate.parse("1999-03-21"))  // 나이를 임시로 동일하게 설정
                .phone("010-0000-0000")  // 전화번호를 임시로 설정
                .profileImg(null)
                .businessNum(businessNum)
                .businessStatus(BusinessStatus.APPROVED)
                .role(role)
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

    private Category createItemCategory(String name) {
        return Category.builder()
                .name(name)
                .build();
    }

    private SubCategory createItemSubCategory(String name, Category category) {
        return SubCategory.builder()
                .name(name)
                .category(category)
                .build();
    }


    private Item createShopItem(String name, String description, Integer price, Category category,
                                SubCategory subCategory, Integer stock, Shop shop) {
        return Item.builder()
                .name(name)
                .img("example-img.png")
                .description(description)
                .price(new BigDecimal(price))
                .category(category)
                .subCategory(subCategory)
                .stock(stock)
                .shop(shop)
                .build();
    }

}

