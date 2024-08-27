package com.example.market.global.auth.oauth2.dto;

import com.example.market.domain.user.dto.UserDto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

// DTO
@AllArgsConstructor
public class CustomOAuth2User implements OAuth2User {
    private final UserDto userDto;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userDto.getRoles();
            }
        });
        return collection;
    }

    public String getUsername() {
        return userDto.getUsername();
    }

    public String getEmail() {
        return userDto.getEmail();
    }

    @Override
    public String getName() {
        return null;
    }
}
