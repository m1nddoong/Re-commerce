package com.example.market.trade.dto;


import com.example.market.trade.entity.TradeItem;
import com.example.market.trade.entity.TradeOffer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeOfferDto {
    private String itemId; // 어떤 물품을
    private String userId; // 누가 구매 제안을 했고
    private String offerStatus; // 구매 제안 상태는 어떤지

    public static TradeOfferDto fromEntity(TradeOffer entity) {
        return TradeOfferDto.builder()
                .itemId(String.valueOf(entity.getItems()))
                .userId(String.valueOf(entity.getId()))
                .offerStatus(String.valueOf(entity.getOfferStatus()))
                .build();
    }
}

