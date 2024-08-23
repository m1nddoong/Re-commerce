package com.example.market.domain.shop.dto;

import com.example.market.domain.shop.constant.ShopCategory;
import com.example.market.domain.shop.constant.ShopStatus;
import com.example.market.domain.shop.entity.Shop;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopDto {
    private Long id;
    private String name;
    private String introduction;
    private ShopCategory shopCategory;
    private ShopStatus status;
    private String username;
    private String address;
    private String coordinates;

    public static ShopDto fromEntity(Shop entity) {
        return ShopDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .introduction(entity.getIntroduction())
                .shopCategory(entity.getShopCategory())
                .status(entity.getStatus())
                .username(entity.getUser().getUsername())
                .address(entity.getAddress())
                .coordinates(entity.getCoordinates())
                .build();
    }
}
