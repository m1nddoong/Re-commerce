package com.example.market.domain.shop.dto;

import com.example.market.domain.shop.entity.Order;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderDto { // 주문 정보
    private Long id;
    private Long userId;
    private Integer totalPrice;
    private List<OrderItemDto> orderItems;

    public static OrderDto fromEntity(Order entity) {
        return OrderDto.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .totalPrice(entity.getTotalPrice())
                .orderItems(entity.getItems().stream()
                        .map(item -> new OrderItemDto(item.getItem().getId(), item.getQuantity()))
                        .collect(Collectors.toList()))
                .build();
    }

}
