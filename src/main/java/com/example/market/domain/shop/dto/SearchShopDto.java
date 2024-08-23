package com.example.market.domain.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchShopDto {
    private String name;
    private String shopCategory;
    private String itemCategory;
    private String itemSubCategory;
}
