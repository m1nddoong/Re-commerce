package com.example.market.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomUser> {

    private final UserDetailsService userDetailsService;

    public WithCustomUserSecurityContextFactory(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public SecurityContext createSecurityContext(WithCustomUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UserDetails userDetails = userDetailsService.loadUserByUsername(annotation.value());

        context.setAuthentication(new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        ));

        return context;
    }
}
