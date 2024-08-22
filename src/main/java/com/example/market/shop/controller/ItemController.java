package com.example.market.shop.controller;


import com.example.market.shop.dto.CreateItemDto;
import com.example.market.shop.dto.ItemDto;
import com.example.market.shop.dto.SearchItemDto;
import com.example.market.shop.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "item", description = "쇼핑몰 상품에 관한 API")
@RestController
@RequestMapping("/api/v1/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    // 상품 등록
    @PostMapping("/create")
    @Operation(
            summary = "쇼핑몰 상품 등록",
            description = "<p>쇼핑몰 주인은 쇼핑몰에 상품을 등록할 수 있다.</p>"
                    + "<p>필수적인 정보는 상품 이름, 상품 이미지, 상품 설명, 상품 가격, 상품 분류, 상품 소분류, 상품 재고가 있다.</p>"
                    + "<p>상품 분류와 소분류는 쇼핑몰의 주인이 추가하거나, 다른 쇼핑몰 주인이 추가했던 분류를 바탕으로 선택할 수 있다.</p>"
    )
    private ResponseEntity<ItemDto> createItem(
            @RequestBody
            CreateItemDto dto
    ) {
        return ResponseEntity.ok(itemService.createItem(dto));
    }

    // 상품 수정
    @PutMapping("/update/{shopItemId}")
    @Operation(
            summary = "쇼핑몰 상품 수정",
            description = "<p>쇼핑몰 주인은 등록된 상품을 수정할 수 있다.</p>"
    )
    private ResponseEntity<ItemDto> updateItem(
            @RequestBody
            CreateItemDto dto,
            @PathVariable(value = "shopItemId")
            Long shopItemId
    ) {
        return ResponseEntity.ok(itemService.updateItem(dto, shopItemId));
    }

    // 상품 삭제
    @DeleteMapping("/delete/{shopItemId}")
    @Operation(
            summary = "쇼핑몰 상품 삭제",
            description = "<p>쇼핑몰 주인은 등록된 상품을 삭제할 수 있다.</p>"
    )
    private ResponseEntity<Void> deleteItem(
            @PathVariable(value = "shopItemId")
            Long shopItemId
    ) {
        itemService.deleteItem(shopItemId);
        return ResponseEntity.ok().build();
    }

    // 쇼핑몰 상품 검색
    @PostMapping("/search")
    @Operation(
            summary = "쇼핑몰 상품 검색",
            description = "<p>비활성 사용자를 제외한 사용자는 쇼핑몰의 상품을 검색할 수 있다.</p>"
                    + "<p>이름, 상품 분류, 상품 소분류, 가격 범위를 기준으로 상품을 검색할 수 있다.</p>"
                    + "<p>단, 분류와 소분류는 하나만 선택이 가능하다.</p>"
                    + "<p>조회되는 상품이 등록된 쇼핑몰에 대한 정보가 함꼐 제공되어야 한다.</p>"
    )
    private ResponseEntity<Page<ItemDto>> getItems(
            @RequestBody
            SearchItemDto dto,
            Pageable pageable
    ) {
        return ResponseEntity.ok(itemService.getItems(dto, pageable));
    }

    // 상품 분류 목록(분류, 소분류) 추가
    @PostMapping("/categories")
    public ResponseEntity<Void> addItemCategory() {
        return null;
    }

    // 상품 분류 목록 확인 - 관리자

    // 상품 분류 목록 수정 - 관리자

    // 할인 적용
}
