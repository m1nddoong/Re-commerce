package com.example.market.trade.controller;

import com.example.market.trade.dto.TradeItemDto;
import com.example.market.trade.service.TradeItemService;
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


@RestController
@RequestMapping("/trade-item")
@RequiredArgsConstructor
public class TradeItemController {
    private final TradeItemService tradeItemService;

    // 물품 조회
    @GetMapping("/{tradeItemId}")
    public ResponseEntity<TradeItemDto> readTradeItem(
            @PathVariable
            Long tradeItemId
    ) {
        return ResponseEntity.ok(tradeItemService.readTradeItem(tradeItemId));
    }

    // 물품 등록
    @PostMapping(
            value = "/create",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE
            }
    )
    public ResponseEntity<TradeItemDto> createTradeItem(
            @Valid @RequestPart("dto")
            TradeItemDto dto,
            @RequestPart(value = "img", required = false)
            MultipartFile tradeItemImg
    ) {
        return ResponseEntity.ok(tradeItemService.createTradeItem(dto, tradeItemImg));
    }

    // 물품 수정
    @PutMapping(
            value = "/update/{tradeItemId}",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE
            }
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
    public ResponseEntity<Void> deleteTradeItem(
            @PathVariable
            Long tradeItemId
    ) {
        tradeItemService.deleteTradeItem(tradeItemId);
        return ResponseEntity.noContent().build();
    }


}
