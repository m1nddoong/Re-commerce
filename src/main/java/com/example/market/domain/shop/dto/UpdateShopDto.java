package com.example.market.domain.shop.dto;

import com.example.market.domain.shop.constant.ShopCategory;
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
    private ShopCategory shopCategory;

}
