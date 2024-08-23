package com.example.market.domain.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchItemDto {
    private String name; // 상품 이름
    private String itemCategory;
    private String itemSubCategory;
    private Integer minPrice;
    private Integer maxPrice;
}
