package com.example.market.global.auth;

import com.example.market.domain.user.entity.CustomUserDetails;
import com.example.market.domain.user.entity.User;
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