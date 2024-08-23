package com.example.market.domain.shop.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ShopCategory {
    // 추후, 카테고리 세부화
    FASHION("패션"),
    ELECTRONICS("전자기기"),
    BEAUTY("뷰티"),
    HOME("가정용품"),
    SPORT("스포츠");

    private final String description;
}
