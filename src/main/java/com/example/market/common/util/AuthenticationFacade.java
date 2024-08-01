package com.example.market.common.util;

import com.example.market.auth.entity.CustomUserDetails;
import com.example.market.auth.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {
    public Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public User extractUser() {
        CustomUserDetails userDetails
                = (CustomUserDetails) getAuth().getPrincipal();
        return userDetails.getUser();
    }
}