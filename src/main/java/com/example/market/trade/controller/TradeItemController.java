package com.example.market.trade.controller;

import com.example.market.trade.dto.TradeItemDto;
import com.example.market.trade.service.TradeItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Tag(name = "trade-item", description = "중고 거래 물품에 관한 API")
@RestController
@RequestMapping("/api/v1/trade-item")
@RequiredArgsConstructor
public class TradeItemController {
    private final TradeItemService tradeItemService;

    // 물품 등록
    @PostMapping(
            value = "/create",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE
            }
    )
    @Operation(
            summary = "중고 거래 물품 등록",
            description = "<p>반일반 사용자는 중고 거래를 목적으로 물품에 대한 정보를 등록할 수 있습니다.</p>"
                    + "<p>제목, 설명, 대표 이미지, 최소 가격이 필요하다</p>"
                    + "<p>대표 이미지를 제외하고 다른 항목은 필수이다.</p>"
                    + "<p>최초로 물품이 등록될 때, 중고 물품의 상태는 <b>판매중</b> 상태가 된다. </p>"
    )
    public ResponseEntity<TradeItemDto> createTradeItem(
            @Valid @RequestPart("dto")
            TradeItemDto dto,
            @RequestPart(value = "img", required = false)
            MultipartFile tradeItemImg
    ) {
        return ResponseEntity.ok(tradeItemService.createTradeItem(dto, tradeItemImg));
    }

    // 물품 조회
    @GetMapping("/{tradeItemId}")
    @Operation(
            summary = "중고 거래 물품 조회",
            description = "<p>등록된 물품 정보는 <b>비활성 사용자</b>를 제외 누구든지 열람할 수 있다.</p>"
                    + "<p>사용자의 상세 정보는 공개되지 않는다.</p>"

    )
    public ResponseEntity<TradeItemDto> readTradeItem(
            @PathVariable
            Long tradeItemId
    ) {
        return ResponseEntity.ok(tradeItemService.readTradeItem(tradeItemId));
    }

    // 물품 수정
    @PutMapping(
            value = "/update/{tradeItemId}",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE
            }
    )
    @Operation(
            summary = "중고 거래 물품 수정",
            description = "<p>등록된 물품 정보는 작성자가 수정 가능하다.</p>"
    )
    public ResponseEntity<TradeItemDto> updateTradeItem(
            @Valid @RequestPart(value = "dto", required = false)
            TradeItemDto dto,
            @RequestPart(value = "img", required = false)
            MultipartFile tradeItemImg,
            @PathVariable
            Long tradeItemId
    ) {
        return ResponseEntity.ok(tradeItemService.updateTradeItem(dto, tradeItemImg, tradeItemId));
    }

    // 물품 삭제
    @DeleteMapping("/delete/{tradeItemId}")
    @Operation(
            summary = "중고 거래 물품 삭제",
            description = "<p>등록된 물품 정보는 작성자가 삭제 가능하다.</p>"
    )
    public ResponseEntity<Void> deleteTradeItem(
            @PathVariable
            Long tradeItemId
    ) {
        tradeItemService.deleteTradeItem(tradeItemId);
        return ResponseEntity.noContent().build();
    }
}
