package com.example.market.shop.dto;


import com.example.market.shop.constant.ItemCategory;
import com.example.market.shop.entity.ShopItem;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


// 필수적인 정보는 상품 이름, 상품 이미지, 상품 설명, 상품 가격, 상품 분류,
// 상품 소분류, 상품 재고가 있다.
@Getter
@Builder
@AllArgsConstructor
public class ShopItemDto {
    @NotBlank
    private String name;
    @NotBlank
    private String img;
    @NotBlank
    private String description;
    @NotBlank
    private String price;
    @NotBlank
    private String category;
    @NotBlank
    private String subCategory;
    @NotBlank
    private Integer stock;

    public static ShopItemDto fromEntity(ShopItem entity) {
        return ShopItemDto.builder()
                .name(entity.getName())
                .img(entity.getImg())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .category(String.valueOf(entity.getCategory()))
                .subCategory(String.valueOf(entity.getSubCategory()))
                .stock(entity.getStock())
                .build();

    }
}
