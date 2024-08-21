package com.example.market.shop.controller;

import com.example.market.shop.dto.OrderDto;
import com.example.market.shop.dto.PurchaseRequestDto;
import com.example.market.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // 상품 주문
    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(
            @RequestBody
            PurchaseRequestDto dto
    ) {
        return ResponseEntity.ok(orderService.createOrder(dto));
    }

    // 상품 주문 전체 취소
    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<Void> cancelOrder(
            @PathVariable(value = "orderId")
            Long orderId
    ) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }


    // 쇼핑몰 구매 요청 수락
    @PutMapping("/approval/{orderId}")
    public ResponseEntity<Void> approvalOrder(
            @PathVariable(value = "orderId")
            Long orderId
    ) {
        orderService.approvalOrder(orderId);
        return ResponseEntity.ok().build();
    }

}
