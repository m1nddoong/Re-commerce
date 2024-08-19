package com.example.market.shop.controller;

import com.example.market.shop.dto.SearchShopDto;
import com.example.market.shop.dto.ShopDto;
import com.example.market.shop.dto.UpdateShopDto;
import com.example.market.shop.entity.Shop;
import com.example.market.shop.service.ShopService;
import jakarta.annotation.Nullable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;

    @PostMapping("/update")
    public ResponseEntity<ShopDto> updateShop(
            @RequestBody
            UpdateShopDto dto
    ) {
        return ResponseEntity.ok(shopService.updateShop(dto));
    }

    @GetMapping("/open-request")
    public ResponseEntity<ShopDto> openRequest() {
        return ResponseEntity.ok(shopService.openRequest());
    }

    @GetMapping("/open-request-list")
    public ResponseEntity<List<ShopDto>> openRequestShopList() {
        return ResponseEntity.ok(shopService.openRequestList());
    }

    @GetMapping("/open-request/{shopId}/approval")
    public ResponseEntity<ShopDto> openRequestApproval(
            @PathVariable(value = "shopId")
            Long shopId
    ) {
        return ResponseEntity.ok(shopService.openRequestApproval(shopId));
    }

    @GetMapping("/open-request/{shopId}/rejection")
    public ResponseEntity<ShopDto> openRequestReject(
            @PathVariable(value = "shopId")
            Long shopId
    ) {
        return ResponseEntity.ok(shopService.openRequestReject(shopId));
    }

    @GetMapping("/close-request")
    public ResponseEntity<ShopDto> closeRequest() {
        return ResponseEntity.ok(shopService.closeRequest());
    }

    @GetMapping("/close-request-list")
    public ResponseEntity<List<ShopDto>> closeRequestList() {
        return ResponseEntity.ok(shopService.closeRequestList());
    }

    @GetMapping("/close-request/{shopId}/approval")
    public ResponseEntity<ShopDto> closeRequestApproval(
            @PathVariable(value = "shopId")
            Long shopId
    ) {
        return ResponseEntity.ok(shopService.closeRequestApproval(shopId));
    }

    // 쇼핑몰 검색(조회)
    // 조건 없이 검색할 경우, 가장 최근에 거래가 있었던 쇼핑몰 순서로 조회된다.
    // 쇼핑몰의 이름, 쇼핑몰 분류, 등록된 상품 분류, 등록된 상품 소분류를 조건으로 쇼핑몰을 검색할 수 있다.
    // 분류와 소분류는 하나만 선택이 가능하다.
    @GetMapping
    public ResponseEntity<List<ShopDto>> getShopList(
            @RequestBody
            SearchShopDto dto
    ) {
        return ResponseEntity.ok(shopService.getShopList(dto));
    }


}
