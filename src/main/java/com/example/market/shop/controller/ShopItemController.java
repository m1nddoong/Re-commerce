package com.example.market.shop.controller;


import com.example.market.shop.dto.ShopItemDto;
import com.example.market.shop.service.ShopItemService;
import com.example.market.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/shop-item")
@RequiredArgsConstructor
public class ShopItemController {
    private final ShopItemService shopItemService;

    // 상품 등록
    @PostMapping("/create")
    private ResponseEntity<ShopItemDto> createItem(
            @RequestBody
            ShopItemDto dto
    ) {
        return ResponseEntity.ok(shopItemService.createItem(dto));
    }

    // 상품 수정
    @PutMapping("/update/{shopItemId}")
    private ResponseEntity<ShopItemDto> updateItem(
            @RequestBody
            ShopItemDto dto,
            @PathVariable
            Long shopItemId
    ) {
        return ResponseEntity.ok(shopItemService.updateItem(dto, shopItemId));
    }

    // 상품 삭제
    @DeleteMapping("/delete/{shopItemId}")
    private ResponseEntity<Void> deleteItem(
            @PathVariable
            Long shopItemId
    ) {
        shopItemService.deleteItem(shopItemId);
        return ResponseEntity.ok().build();
    }

    // 상품 조회

    // 상품 분류 목록(분류, 소분류) 추가

    // 상품 분류 목록 확인 - 관리자

    // 상품 분류 목록 수정 - 관리자

    // 할인 적용
}
