package com.example.market.domain.shop.dto;


import com.example.market.domain.shop.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


// 필수적인 정보는 상품 이름, 상품 이미지, 상품 설명, 상품 가격, 상품 분류,
// 상품 소분류, 상품 재고가 있다.
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private String name;
    private String img;
    private String description;
    private Integer price;
    private String itemCategory;
    private String itemSubCategory;
    private Integer stock;
    private Long shopId;



    public static ItemDto fromEntity(Item entity) {
        return ItemDto.builder()
                .name(entity.getName())
                .img(entity.getImg())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .itemCategory(entity.getCategory().getName())
                .itemSubCategory(entity.getSubCategory().getName())
                .stock(entity.getStock())
                .shopId(entity.getShop().getId())
                .build();

    }
}
