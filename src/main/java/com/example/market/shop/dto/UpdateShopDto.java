package com.example.market.shop.dto;

import com.example.market.shop.constant.ShopCategory;
import com.example.market.shop.entity.Shop;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateShopDto {
    @NotBlank
    private String name;
    private String introduction;
    private ShopCategory category;

}
