package com.example.market.shop.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PurchaseRequestDto {
    private Integer payAmount;
    private List<OrderItemDto> orderItems;
}
