package com.example.market.domain.auth.service;

import com.example.market.domain.auth.entity.User;
import com.example.market.domain.auth.dto.PrincipalDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {
    public Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public User extractUser() {
        PrincipalDetails userDetails = (PrincipalDetails) getAuth().getPrincipal();
        return userDetails.getUser();
    }
}