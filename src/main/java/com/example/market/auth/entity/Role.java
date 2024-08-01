package com.example.market.auth.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    INACTIVE_USER ("ROLE_INACTIVE"),
    ACTIVE_USER ("ROLE_ACTIVE"),
    BUSINESS_USER ("ROLE_ACTIVE,ROLE_OWNER"),
    ADMIN ("ROLE_ACTIVE,ROLE_OWNER,ROLE_ADMIN");

    private final String roles;
}


/*
INSERT INTO role
VALUES
    -- 비활성 사용자
    (1, 'ROLE_INACTIVE'),
    -- 활성 사용자 (일반, 사업자, 관리자)
    (2, 'ROLE_ACTIVE'),
    (3, 'ROLE_OWNER'),
    (4, 'ROLE_ADMIN');

-- SQLite 는 싱글 쿼터를 ('') 사용하며
-- 쿼리문 마지막에 콤마(;)를 기입해야지 정상적으로 작동한다.
 */
