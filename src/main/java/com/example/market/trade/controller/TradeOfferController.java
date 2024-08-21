package com.example.market.trade.controller;

import com.example.market.trade.dto.TradeOfferDto;
import com.example.market.trade.service.TradeOfferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "trade-offer", description = "중고 거래 품물 구매에 관한 API")
@RestController
@RequestMapping("/api/v1/trade-offer")
@RequiredArgsConstructor
public class TradeOfferController {
    private final TradeOfferService tradeOfferService;

    // 구매 제안 등록 (어떤 물품에 대한 제안인지 pathVariable)
    @GetMapping("/{tradeItemId}")
    @Operation(
            summary = "구매 제안 등록",
            description = "<p><b>물품을 등록한 사용자</b>와 <b>비활성 사용자</b> 제외, 등록된 물품에 대하여 구매 제안을 등록할 수 있다.</p>"
    )
    public ResponseEntity<TradeOfferDto> requestTradeOffer(
            @PathVariable("tradeItemId")
            Long tradeItemId
    ) {
        return ResponseEntity.ok(tradeOfferService.requestTradeOffer(tradeItemId));
    }


    // 구매 제안 조회 (제안 등록자, 물품 등록자만 가능)
    @GetMapping("/{tradeItemId}/list")
    @Operation(
            summary = "구매 제안 조회",
            description = "<p>등록된 구매 제안은 <b>물품을 등록한 사용자</b>와 <b제안을 등록한 사용자</b>만 조회가 가능하다.</p>"
                    + "<p><b>제안을 등록한 사용자</b>는 자신의 제안만 확인이 가능하다.</p>"
                    + "<p><b>물품을 등록한 사용자</b>는 모든 제안이 확인 가능하다.</p>"
    )
    public ResponseEntity<List<TradeOfferDto>> getTadeOfferList(
            @PathVariable("tradeItemId")
            Long tradeItemId
    ) {
        return ResponseEntity.ok(tradeOfferService.getTradeOfferList(tradeItemId));
    }

    // 구매 제안 수락
    @GetMapping("/{tradeOfferId}/approval")
    @Operation(
            summary = "구매 제안 수락",
            description = "<p><b>물픔을 등록한 사용자</b>는 등록된 구매 제안을 수락할 수 있다.</p>"
                    + "<p>이때 구매 제안의 상태는 <b>수락</b>이 된다.</p>"
    )
    public ResponseEntity<TradeOfferDto> approvalTradeOffer(
            @PathVariable("tradeOfferId")
            Long tradeOfferId
    ) {
        return ResponseEntity.ok(tradeOfferService.approvalTradeOffer(tradeOfferId));
    }


    // 구매 제안 거절
    @GetMapping("/{tradeOfferId}/reject")
    @Operation(
            summary = "구매 제안 거절",
            description = "<p><b>물픔을 등록한 사용자</b>는 등록된 구매 제안을 거절할 수 있다.</p>"
                    + "<p>이때 구매 제안의 상태는 <b>거절</b>이 된다.</p>"
    )
    public ResponseEntity<TradeOfferDto> rejectionTradeOffer(
            @PathVariable("tradeOfferId")
            Long tradeOfferId
    ) {
        return ResponseEntity.ok(tradeOfferService.rejectTradeOffer(tradeOfferId));
    }

    // 구매 제안 확정하기 (구매 제안 상태 : 확정 / 물품 상태 : 판매 완료)
    @GetMapping("/{tradeOfferId}/confirm")
    @Operation(
            summary = "구매 제안 확정",
            description = "<p><b>제안을 등록한 사용자</b>는 자신이 등록한 제안이 수락 상태일 경우, 구매를 확정할 수 있다.</p>"
                    + "<p>이떄 구매 제안의 상태는 <b>확정</b> 상태가 된다.</p>"
                    + "<p>구매 제안이 확정될 경우, 대상 물품의 상태는 <b>판매 완료</b>가 된다.</p>"
                    + "<p>구매 제안이 확정될 경우, 확정되지 않은 다른 구매 제안의 상태는 모두 <b>거절</b>이 된다.</p>"
    )
    public ResponseEntity<TradeOfferDto> confirmTradeOffer(
            @PathVariable
            Long tradeOfferId
    ) {
        return ResponseEntity.ok(tradeOfferService.confirmTradeOffer(tradeOfferId));

    }
}
