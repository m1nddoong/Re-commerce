package com.example.market.shop.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ShopStatus {
    PREPARING,          // 준비
    OPEN_REQUEST,       // 개설 요청
    OPEN_NOT_ALLOWED,   // 개설 요청 불허
    OPEN,               // 개설 요청 승인 -> 개설
    CLOSE_REQUEST,      // 폐쇄 요청
    CLOSED              // 폐쇄 요청 승인 -> 폐쇄
}
