package com.example.market.domain.auth.constant;

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
