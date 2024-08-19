package com.example.market.shop.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequestDto {
    private Integer payAmount;
    private List<OrderItemDto> orderItems;
}
