package com.example.market.global.auth;

import com.example.market.domain.user.entity.User;
import com.example.market.global.auth.oauth2.dto.PrincipalDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {
    public Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public User extractUser() {
        PrincipalDetails userDetails
                = (PrincipalDetails) getAuth().getPrincipal();
        return userDetails.getUser();
    }
}