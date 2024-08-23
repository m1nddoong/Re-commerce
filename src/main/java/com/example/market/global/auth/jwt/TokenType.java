package com.example.market.global.auth.jwt;

import lombok.Getter;

// 토큰 타입에 따른 밀리초를 반환하는 enum 클래스
@Getter
public enum TokenType {
    REFRESH(24 * 60 * 60 * 1000), // 24시간 -> 밀리초
    // ACCESS(30 * 1000); // 30초 -> 밀리초
    ACCESS(60 * 60 * 1000); // 1시간 -> 밀리초


    private final long tokenValidMillis;

    TokenType(long tokenValidMillis) {
        this.tokenValidMillis = tokenValidMillis;
    }

}

