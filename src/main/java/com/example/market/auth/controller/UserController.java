package com.example.market.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("users")
public class UserController {
    @GetMapping("/login")
    public String loginForm() {
        return "login-form";
    }

    // 로그인 한 후에 내가 누군지 확인하기 위함
    @GetMapping("/my-profile")
    public String myProfile() {
        return "my-profile";
    }
}
