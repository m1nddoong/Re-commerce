package com.example.market.unittest;

import com.example.market.auth.entity.User;
import java.util.Collections;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;


// @WithCustomUserDetails 애너테이션을 기반으로
// SecurityCOntext 를 생성하는 팩토리 클래스
public class WithCustomUserDetailsFactory
        implements WithSecurityContextFactory<WithCustomUserDetails> {

    @Override
    public SecurityContext createSecurityContext(
            WithCustomUserDetails annotation
    ) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        UserDetails principal = (UserDetails) User.builder()
                .username(annotation.username())
                .password(annotation.password())
                .authorities(
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + annotation.authorities()[0]))
                                .toString())
                .build();

        Authentication auth = new UsernamePasswordAuthenticationToken(
                principal,
                annotation.password(),
                principal.getAuthorities()
        );
        context.setAuthentication(auth);
        return context;
    }
}
