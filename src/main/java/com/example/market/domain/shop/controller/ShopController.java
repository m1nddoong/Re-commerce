package com.example.market.domain.shop.controller;

import com.example.market.domain.shop.dto.ShopDto;
import com.example.market.domain.shop.dto.SearchShopDto;
import com.example.market.domain.shop.dto.UpdateShopDto;
import com.example.market.domain.shop.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "shop", description = "쇼핑몰 관리에 관한 API")
@RestController
@RequestMapping("/api/v1/shop")
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;

    @PostMapping("/update")
    @Operation(
            summary = "쇼핑몰 수정",
            description = "<p>쇼핑몰에는 이름, 소개, 분류의 정보를 가지고 있으며 주인은 자유롭게 수정이 가능하다.</p>"
    )
    public ResponseEntity<ShopDto> updateShop(
            @RequestBody
            UpdateShopDto dto
    ) {
        return ResponseEntity.ok(shopService.updateShop(dto));
    }

    @GetMapping("/open-request")
    @Operation(
            summary = "쇼핑몰 개설 요청",
            description = "<p>쇼핑몰의 이름, 소개, 분류가 전부 작성된 상태라면 쇼핑몰 개설 신청을 할 수 있다.</p>"
    )
    public ResponseEntity<ShopDto> openRequest() {
        return ResponseEntity.ok(shopService.openRequest());
    }

    @GetMapping("/open-request-list")
    @Operation(
            summary = "쇼핑몰 개설 요청 목록 조회",
            description = "<p>관리자는 개설 신청된 쇼핑몰의 목록을 확인할 수 있다.</p>"
    )
    public ResponseEntity<List<ShopDto>> openRequestShopList() {
        return ResponseEntity.ok(shopService.openRequestList());
    }

    @GetMapping("/open-request/{shopId}/approval")
    @Operation(
            summary = "쇼핑몰 개설 요청 허가",
            description = "<p>관리자는 개설 신청된 쇼핑몰의 목록을 확인하고 정보를 확인한 후 신청을 허가할 수 있다.</p>"
    )
    public ResponseEntity<ShopDto> openRequestApproval(
            @PathVariable(value = "shopId")
            Long shopId
    ) {
        return ResponseEntity.ok(shopService.openRequestApproval(shopId));
    }

    @GetMapping("/open-request/{shopId}/rejection")
    @Operation(
            summary = "쇼핑몰 개설 요청 불허",
            description = "<p>관리자는 개설 신청된 쇼핑몰의 목록을 확인하고 정보를 확인한 후 신청을 불허할 수 있다.</p>"
                    + "<p>불허할 경우 그 이유를 함꼐 작성해야 한다.</p>"
                    + "<p>불허된 이유를 쇼핑몰의 주인이 확인할 수 있어야 한다.</p>"
    )
    public ResponseEntity<ShopDto> openRequestReject(
            @PathVariable(value = "shopId")
            Long shopId
    ) {
        return ResponseEntity.ok(shopService.openRequestReject(shopId));
    }

    @GetMapping("/close-request")
    @Operation(
            summary = "쇼핑몰 폐쇄 요청",
            description = "<p>쇼핑몰 주인은 사유를 작성하여 쇼핑몰 페쇄 요처을 할 수 있다.</p>"
    )
    public ResponseEntity<ShopDto> closeRequest() {
        return ResponseEntity.ok(shopService.closeRequest());
    }

    @GetMapping("/close-request-list")
    @Operation(
            summary = "쇼핑몰 폐쇄 요청 목록 조회",
            description = "<p>관리자는 쇼핑몰 페쇄 요청 목록을 조회할 수 있다.</p>"
    )
    public ResponseEntity<List<ShopDto>> closeRequestList() {
        return ResponseEntity.ok(shopService.closeRequestList());
    }

    @GetMapping("/close-request/{shopId}/approval")
    @Operation(
            summary = "쇼핑몰 폐쇄 요청 허가",
            description = "<p>관리자는 쇼핑몰 페쇄 요청 목록을 확인한 후 요청을 허가할 수 있다.</p>"
    )
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
    @PostMapping("/search")
    @Operation(
            summary = "쇼핑몰 검색",
            description = "<p>비활성 사용자를 제외한 사용자는 쇼핑몰을 조회할 수 있다.</p>"
                    + "<p>조건 없이 조회할 경우, 가장 최근에 거래가 있었던 쇼핑몰 순서로 조회된다.</p>"
                    + "<p>이름, 쇼핑몰 분류, 등록된 상품 분류, 등록된 상품 소분류를 조건으로 쇼핑몰을 검색할 수 있다.</p>"
                    + "<p>단, 분류와 소분류는 하나만 선택이 가능하다.</p>"
    )
    public ResponseEntity<Page<ShopDto>> getShopList(
            @RequestBody
            SearchShopDto dto,
            Pageable pageable
    ) {
        return ResponseEntity.ok(shopService.getShopList(dto, pageable));
    }


}
