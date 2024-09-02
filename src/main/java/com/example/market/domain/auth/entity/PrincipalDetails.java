package com.example.market.domain.auth.entity;

import com.example.market.domain.auth.entity.User;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
@Builder
public class PrincipalDetails implements UserDetails, OAuth2User {
    private User user;
    private Map<String,Object> attributes;

    // 일반 로그인
    public PrincipalDetails(User user) {
        this.user = user;
        this.attributes = null;
    }

    // OAuth2 로그인
    public PrincipalDetails (User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    // OAuthUser 의 메서드
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return user.getEmail();
    }

    // UserDetails 의 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(user.getRole().getRoles().split(","))
                .sorted()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    public String getUsername() {
        return user.getUsername();
    }




}
