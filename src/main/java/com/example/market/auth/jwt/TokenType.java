package com.example.market.auth.jwt;

public enum TokenType {
    REFRESH(24 * 60 * 60 * 1000), // 24시간 -> 밀리초
    ACCESS(60 * 60 * 1000); // 1시간 -> 밀리초

    private final long tokenValidMillis;

    TokenType(long tokenValidMillis) {
        this.tokenValidMillis = tokenValidMillis;
    }

    public long getTokenValidMillis() {
        return tokenValidMillis;
    }
}

