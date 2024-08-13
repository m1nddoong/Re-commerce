package com.example.market.shop.constant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemCategory {
    // 패션 상품
    MENS_CLOTHING("남성 의류"),
    WOMENS_CLOTHING("남성 의류"),
    KIDS_CLOTHING("아동 의류"),

    // 전자기기
    ELECTRONIC_MOBILE_PHONES("휴대폰"),
    ELECTRONIC_COMPUTERS("컴퓨터"),
    ELECTRONIC_ACCESSORIES("액세서리"),

    // 뷰티
    BEAUTY_SKINCARE("스킨케어"),
    BEAUTY_MAKEUP("메이크업"),
    BEAUTY_HAIRCARE("헤어케어"),

    // 가정용품
    FURNITURE("가구"),
    KITCHENWARE("주방용품"),
    DECOR("인테리어 소품"),

    // 스포츠
    SPORTS_WEAR("스포츠웨어"),
    SPORTS_EQUIPMENT("운동 기구"),
    SPORTS_ACCESSORIES("스포츠 액세서리");


    private final String description;
}
