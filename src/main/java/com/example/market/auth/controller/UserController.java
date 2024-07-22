package com.example.market.auth.controller;

import com.example.market.auth.entity.CustomUserDetails;
import com.example.market.auth.service.JpaUserDetailsManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserDetailsManager manager;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/home")
    public String home() {
        log.info(SecurityContextHolder.getContext().getAuthentication().getName());
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login-form";
    }

    // 로그인 한 후에 내가 누군지 확인하기 위함
    @GetMapping("/my-profile")
    public String myProfile(
            Authentication authentication,
            Model model
    ) {
        // 현재 사용자 인증 정보 확인
        model.addAttribute("username", authentication.getName());
        log.info(authentication.getName());
        // UserDetails 를 커스텀(구현체) 제작했다면 해당 UserDetails 의 객체를 가져올 수 있다.
        log.info(((CustomUserDetails) authentication.getPrincipal()).getUsername());
        // log.info(((CustomUserDetails) authentication.getPrincipal()).getPhone());
        return "my-profile";
    }

    @GetMapping("/register")
    public String signUpForm() {
        return "register-form";
    }

    @PostMapping("/register")
    public String signUpReqeust(
            @RequestParam("username")
            String username,
            @RequestParam("password")
            String password,
            @RequestParam("password-check")
            String passwordCheck
    ) {
        if (password.equals(passwordCheck)) {
            manager.createUser(CustomUserDetails.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .build()
            );
        }
        return "redirect:/users/login";
    }
}
