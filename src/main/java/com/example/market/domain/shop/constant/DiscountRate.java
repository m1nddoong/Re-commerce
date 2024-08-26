package com.example.market.domain.shop.constant;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DiscountRate {
    NO_DISCOUNT(BigDecimal.ZERO),
    TEN_PERCENT(BigDecimal.valueOf(10)),
    TWENTY_PERCENT(BigDecimal.valueOf(20)),
    THIRTY_PERCENT(BigDecimal.valueOf(30));

    private final BigDecimal discountPercentage;

    // 할인율을 적용하는 메서드
    public BigDecimal applyDiscount(BigDecimal price) {
        BigDecimal discountMultiplier = BigDecimal.ONE.subtract(
                this.discountPercentage.divide(BigDecimal.valueOf(100)));
        return price.multiply(discountMultiplier).setScale(0, RoundingMode.HALF_UP);
    }

    // 정적 메서드 : BigDecimal 값을 DiscountRate 로 변환
    public static DiscountRate of(BigDecimal percentage) {
        if (percentage.compareTo(BigDecimal.valueOf(10)) == 0) return TEN_PERCENT;
        if (percentage.compareTo(BigDecimal.valueOf(20)) == 0) return TWENTY_PERCENT;
        if (percentage.compareTo(BigDecimal.valueOf(30)) == 0) return THIRTY_PERCENT;
        return NO_DISCOUNT;

    }
}
