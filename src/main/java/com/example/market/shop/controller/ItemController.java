package com.example.market.shop.controller;


import com.example.market.shop.dto.CreateItemDto;
import com.example.market.shop.dto.ItemDto;
import com.example.market.shop.dto.SearchItemDto;
import com.example.market.shop.service.ItemService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    // 상품 등록
    @PostMapping("/create")
    private ResponseEntity<ItemDto> createItem(
            @RequestBody
            CreateItemDto dto
    ) {
        return ResponseEntity.ok(itemService.createItem(dto));
    }

    // 상품 수정
    @PutMapping("/update/{shopItemId}")
    private ResponseEntity<ItemDto> updateItem(
            @RequestBody
            CreateItemDto dto,
            @PathVariable
            Long shopItemId
    ) {
        return ResponseEntity.ok(itemService.updateItem(dto, shopItemId));
    }

    // 상품 삭제
    @DeleteMapping("/delete/{shopItemId}")
    private ResponseEntity<Void> deleteItem(
            @PathVariable
            Long shopItemId
    ) {
        itemService.deleteItem(shopItemId);
        return ResponseEntity.ok().build();
    }

    // 쇼핑몰 상품 검색
    @GetMapping("/")
    private ResponseEntity<Page<ItemDto>> getItems(
            @RequestBody
            SearchItemDto dto,
            Pageable pageable
    ) {
        return ResponseEntity.ok(itemService.getItems(dto, pageable));
    }

    // 상품 분류 목록(분류, 소분류) 추가

    // 상품 분류 목록 확인 - 관리자

    // 상품 분류 목록 수정 - 관리자

    // 할인 적용
}
