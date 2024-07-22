package com.example.market.auth.service;

import com.example.market.auth.entity.CustomUserDetails;
import com.example.market.auth.entity.UserEntity;
import com.example.market.auth.repo.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Slf4j
@Service
// @RequiredArgsConstructor
public class JpaUserDetailsManager implements UserDetailsManager {
    private final UserRepository userRepository;
    public JpaUserDetailsManager(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        createUser(CustomUserDetails.builder()
                .username("user1")
                .password(passwordEncoder.encode("1234"))
                // .email("user1@gmail.com")
                // .phone("01012345678")
                .build());
    }

    @Override
    // formLogin 등 Spring Security 내부에서 인증을 처리할 때 사용하는 메서드
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Optional<UserEntity> optionalUser
                = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) throw new UsernameNotFoundException(username);
        UserEntity userEntity = optionalUser.get();
        return CustomUserDetails.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                // .email(userEntity.getEmail())
                // .phone(userEntity.getPhone())
                .build();
    }

    @Override
    public void createUser(UserDetails user) {
        if (userExists(user.getUsername())) {
            throw new IllegalArgumentException("User with username" + user.getUsername());
        }
        try {
            CustomUserDetails userDetails = (CustomUserDetails) user;
            UserEntity newUser = UserEntity.builder()
                    .username(userDetails.getUsername())
                    .password(userDetails.getPassword())
                    // .email(userDetails.getEmail())
                    // .phone(userDetails.getPhone())
                    .build();
            userRepository.save(newUser);
        } catch (ClassCastException e) {
            log.error("failed to cast to {}", CustomUserDetails.class);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return this.userRepository.existsByUsername(username);
    }


}
