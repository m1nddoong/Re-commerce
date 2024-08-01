package com.example.market.trade.dto;


import com.example.market.auth.entity.User;
import com.example.market.trade.entity.ItemStatus;
import com.example.market.trade.entity.TradeItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeItemDto {
    private String title;
    private String description;
    private String image;
    private Long price;
    private ItemStatus itemStatus;
    // 사용자의 상세정보를 숨기기 위함, User 객체 대신 필요한 정보만 포
    private String user;

    public static TradeItemDto fromEntity(TradeItem entity) {
        return TradeItemDto.builder()
                .title(entity.getTitle())
                .description(entity.getDescription())
                .image(entity.getImage())
                .price(entity.getPrice())
                .itemStatus(entity.getItemStatus())
                .user(entity.getUser().getUsername())
                .build();
    }
}


