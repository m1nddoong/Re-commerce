package com.example.market.shop.dto;

import com.example.market.shop.constant.ShopCategory;
import com.example.market.shop.constant.ShopStatus;
import com.example.market.shop.entity.Shop;
import com.example.market.shop.entity.ShopItem;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopDto {
    private String name;
    private String introduction;
    private ShopCategory category;
    private ShopStatus status;
    private String username;
    private String address;
    private String coordinates;

    public static ShopDto fromEntity(Shop entity) {
        return ShopDto.builder()
                .name(entity.getName())
                .introduction(entity.getIntroduction())
                .category(entity.getCategory())
                .status(entity.getStatus())
                .username(entity.getUser().getUsername())
                .address(entity.getAddress())
                .coordinates(entity.getCoordinates())
                .build();
    }
}
