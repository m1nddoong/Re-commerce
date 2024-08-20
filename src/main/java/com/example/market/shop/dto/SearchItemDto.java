package com.example.market.shop.dto;

import com.example.market.shop.constant.ItemCategory;
import com.example.market.shop.constant.ItemSubCategory;
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
    private ItemCategory itemCategory;
    private ItemSubCategory itemSubCategory;
    private Long minPrice;
    private Long maxPrice;
}
