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

    // 문자열로 주어진 역할에 해당하는 권한을 조회한다.
    public static String getIncludingRoles (String role) {
        return Role.valueOf(role).getRoles();
    }

    // 기존 Role 객체의 권한에 새로운 권한을 추가한다.
    public static String addRole(Role role, String addRole) {
        String priorRoles = role.getRoles();
        priorRoles += "," + addRole;
        return priorRoles;
    }

    public static String addRole(String roles, Role role) {
        return roles + "," + role.getRoles();
    }
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
