package com.example.market.domain.auth.jwt;

import lombok.Getter;

// 토큰 타입에 따른 밀리초를 반환하는 enum 클래스
@Getter
public enum TokenType {
    REFRESH(60 * 60 * 1000), // 60분
    // ACCESS(60 * 1000); // 60초 -> 밀리초
    ACCESS(20 * 60 * 1000); // 20분


    private final long tokenValidMillis;

    TokenType(long tokenValidMillis) {
        this.tokenValidMillis = tokenValidMillis;
    }

}

