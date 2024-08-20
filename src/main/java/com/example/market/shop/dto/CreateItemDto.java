package com.example.market.shop.dto;


import com.example.market.shop.entity.Item;
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
@NoArgsConstructor
public class CreateItemDto {
    @NotBlank
    private String name;
    @NotBlank
    private String img;
    @NotBlank
    private String description;
    @NotBlank
    private Integer price;
    @NotBlank
    private String itemCategory;
    @NotBlank
    private String itemSubCategory;
    @NotBlank
    private Integer stock;
}
