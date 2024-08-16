package com.example.market.shop.controller;

import com.example.market.shop.dto.OrderDto;
import com.example.market.shop.dto.PurchaseRequestDto;
import com.example.market.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    // 쇼핑몰 상품 구매 요청
    @PostMapping("/purchase-request")
    public ResponseEntity<OrderDto> purchaseItem(
            @RequestBody
            PurchaseRequestDto dto
    ) {
        return ResponseEntity.ok(orderService.purchaseRequest(dto));
    }

}
