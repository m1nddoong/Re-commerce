package com.example.market.trade.controller;

import com.example.market.trade.dto.TradeOfferDto;
import com.example.market.trade.service.TradeOfferService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/trade-offer")
@RequiredArgsConstructor
public class TradeOfferController {
    private final TradeOfferService tradeOfferService;

    // 구매 제안 등록 (어떤 물품에 대한 제안인지 pathVariable)
    @GetMapping("/{tradeItemId}")
    public ResponseEntity<TradeOfferDto> requestTradeOffer(
            @PathVariable("tradeItemId")
            Long tradeItemId
    ) {
        return ResponseEntity.ok(tradeOfferService.requestTradeOffer(tradeItemId));
    }


    // 구매 제안 조회 (제안 등록자, 물품 등록자만 가능)
    //
    @GetMapping("/{tradeItemId}/list")
    public ResponseEntity<List<TradeOfferDto>> getTadeOfferList(
            @PathVariable("tradeItemId")
            Long tradeItemId
    ) {
        return ResponseEntity.ok(tradeOfferService.getTradeOfferList(tradeItemId));
    }

    // 구매 제안 수락
    @GetMapping("/{tradeOfferId}/approval")
    public ResponseEntity<TradeOfferDto> approvalTradeOffer(
            @PathVariable("tradeOfferId")
            Long tradeOfferId
    ) {
        return ResponseEntity.ok(tradeOfferService.approvalTradeOffer(tradeOfferId));
    }


    // 구매 제안 거절
    @GetMapping("/{tradeOfferId}/reject")
    public ResponseEntity<TradeOfferDto> rejectionTradeOffer(
            @PathVariable("tradeOfferId")
            Long tradeOfferId
    ) {
        return ResponseEntity.ok(tradeOfferService.rejectTradeOffer(tradeOfferId));
    }

    // 구매 제안 확정하기 (구매 제안 상태 : 확정 / 물품 상태 : 판매 완료)
    @GetMapping("/{tradeOfferId}/confirm")
    public ResponseEntity<TradeOfferDto> confirmTradeOffer(
            @PathVariable
            Long tradeOfferId
    ) {
        return ResponseEntity.ok(tradeOfferService.confirmTradeOffer(tradeOfferId));

    }
}
