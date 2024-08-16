package com.example.market.shop.controller;

import com.example.market.shop.dto.ShopDto;
import com.example.market.shop.dto.UpdateShopDto;
import com.example.market.shop.entity.Shop;
import com.example.market.shop.service.ShopService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    // 관리자의 쇼핑몰 개설 신청 목록 확인
    @GetMapping("/open-request-list")
    public ResponseEntity<List<ShopDto>> openRequestShopList() {
        return ResponseEntity.ok(shopService.openRequestList());
    }

    // 관리자의 쇼핑몰 개설 허가 -> 쇼핑몰 오픈 상태
    @GetMapping("/open-request/{shopId}/approval")
    public ResponseEntity<ShopDto> openRequestApproval(
            @PathVariable(value = "shopId")
            Long shopId
    ) {
        return ResponseEntity.ok(shopService.openRequestApproval(shopId));
    }


    // 관리자의 쇼핑모 개설 불허 -> 불허 사유 주인에게 제출
    @GetMapping("/open-request/{shopId}/rejection")
    public ResponseEntity<ShopDto> openRequestReject(
            @PathVariable(value = "shopId")
            Long shopId
    ) {
        return ResponseEntity.ok(shopService.openRequestReject(shopId));
    }

    // 쇼핑몰 주인의 쇼핑몰 폐쇄 요청
    @GetMapping("/close-request")
    public ResponseEntity<ShopDto> closeRequest() {
        return ResponseEntity.ok(shopService.closeRequest());
    }

    // 관리자의 쇼핑몰 페쇄 요청 확인
    @GetMapping("/close-request-list")
    public ResponseEntity<List<ShopDto>> closeRequestList() {
        return ResponseEntity.ok(shopService.closeRequestList());
    }

    // 관리자의 쇼핑몰 폐쇄 요청 승인
    @GetMapping("/close-request/{shopId}/approval")
    public ResponseEntity<ShopDto> closeRequsetApproval(
            @PathVariable(value = "shopId")
            Long shopId
    ) {
        return ResponseEntity.ok(shopService.closeRequestApproval(shopId));
    }

    // 쇼핑몰 조회
    // 조건 없이 조회할 경우, 가장 최근에 거래가 있었던 쇼핑몰 순서로 조회



}
