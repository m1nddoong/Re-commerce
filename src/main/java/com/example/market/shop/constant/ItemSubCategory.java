package com.example.market.shop.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemSubCategory {
    // 패션
    MENS_TOPS("남성 상의"),
    MENS_BOTTOMS("남성 하의"),
    MENS_ACCESSORIES("남성 악세사리"),

    WOMENS_TOPS("여성 상의"),
    WOMENS_BOTTOMS("여성 하의"),
    WOMENS_ACCESSORIES("여성 악세사리"),

    KIDS_TOPS("아동 상의"),
    KIDS_BOTTOMS("아동 하의"),
    KIDS_ACCESSORIES("아동 악세사리"),

    // 전자기기
    MOBILE_PHONES("휴대폰"),
    SMARTWATCHES("스마트워치"),
    LAPTOPS("노트북"),
    HEADPHONES("헤드폰"),

    // 뷰티
    SKINCARE_CREAM("스킨케어 크림"),
    SERUMS("세럼"),
    FOUNDATION("파운데이션"),
    HAIR_SHAMPOO("샴푸"),

    // 가정용품
    SOFAS("소파"),
    TABLES("테이블"),
    PANS("팬"),
    CUTLERY("식기"),

    // 스포츠
    SPORTSWEAR("스포츠웨어"),
    FITNESS_EQUIPMENT("피트니스 장비"),
    BASKETBALLS("농구공"),
    TENNIS_RACKETS("테니스 라켓");


    private final String description;
}
