package com.example.market.auth.controller;

import com.example.market.auth.entity.CustomMemberDetails;
import com.example.market.auth.entity.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {
    public Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

//    public Member extractUser() {
//        CustomMemberDetails userDetails
//                = (CustomMemberDetails) getAuth().getPrincipal();
//        return userDetails.getUserEntity();
//    }
}