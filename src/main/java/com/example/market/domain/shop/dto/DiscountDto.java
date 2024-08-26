package com.example.market.domain.shop.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountDto {
    private Long itemId;
    private LocalDateTime expirationDate;
    private BigDecimal discountRete;
}
