package com.example.market.trade.controller;

import com.example.market.trade.dto.TradeOfferDto;
import com.example.market.trade.service.TradeOfferService;
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
            @PathVariable
            Long tradeItemId
    ) {
        return ResponseEntity.ok(tradeOfferService.requestTradeOffer(tradeItemId));
    }


    // 구매 제안 조회 (제안을 등록한 사용자, 물품을 등록한 사용자만 조회 가능)
    @GetMapping("/{tradeItemId}/list")
    public void getTadeOfferList() {

    }

    // 구매 제안 수락
    @GetMapping("/{tradeItemId}/approval")
    public void approvalTradeOffer() {

    }


    // 구매 제안 거절
    @GetMapping("/{tradeItemId}/reject")
    public void rejectionTradeOffer() {

    }

    // 구매 제안 확정하기 (구매 제안 상태 : 확정 / 물품 상태 : 판매 완료)

    @GetMapping("/{tradeItemId}/confirm")
    public void confirmTradeOffer() {

    }
}
