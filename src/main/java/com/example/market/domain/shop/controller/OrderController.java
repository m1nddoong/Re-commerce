package com.example.market.domain.shop.controller;

import com.example.market.domain.shop.dto.PurchaseRequestDto;
import com.example.market.domain.shop.service.OrderService;
import com.example.market.domain.shop.dto.OrderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "order", description = "쇼핑몰 상품 주문에 관한 API")
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // 상품 주문
    @PostMapping("/create")
    @Operation(
            summary = "상품 구매 요청",
            description = "<p>비활성 사용자를 제외한 사용자는 쇼핑몰의 상품을 구매할 수 있다.</p>"
                    + "<p>상품과 구매 수량을 기준으로 구매 요청을 할 수 있다.</p>"
                    + "<p>구매 요청 후 사용자는 구매에 필요한 금액을 전달한다고 가정한다.</p>"
    )
    public ResponseEntity<OrderDto> createOrder(
            @RequestBody
            PurchaseRequestDto dto
    ) {
        return ResponseEntity.ok(orderService.createOrder(dto));
    }

    // 상품 주문 전체 취소
    @PutMapping("/{orderId}/cancel")
    @Operation(
            summary = "상품 구매 요청 취소",
            description = "<p>구매 요청이 수락되기 전에는 구매 요청을 취소할 수 있다.</p>"
    )
    public ResponseEntity<String> cancelOrder(
            @PathVariable(value = "orderId")
            Long orderId
    ) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }


    // 쇼핑몰 구매 요청 수락
    @PutMapping("/{orderId}/approve")
    @Operation(
            summary = "상품 구매 요청 수락",
            description = "<p>주인이 구매 요청으로부터 전달된 금액을 확인하면 구매 요청을 수락할 수 있다.</p>"
                    + "<p>구매 요청이 수락되면, 상품 재고가 자동으로 갱신된다. 이후엔 구매 취소가 불가능하다.</p>"
    )
            public ResponseEntity<String> approvalOrder(
            @PathVariable(value = "orderId")
            Long orderId
    ) {
        return ResponseEntity.ok(orderService.approvalOrder(orderId));
    }

}
