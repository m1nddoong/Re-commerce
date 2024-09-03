package com.example.market.domain.used_trade.dto;


import com.example.market.domain.used_trade.entity.TradeItem;
import com.example.market.domain.used_trade.entity.TradeItem.ItemStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeItemDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private String image;
    @NotNull
    private Long price;
    private ItemStatus itemStatus;

    // 사용자의 상세정보를 숨기기 위함, User 객체 대신 필요한 정보만 포함
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


