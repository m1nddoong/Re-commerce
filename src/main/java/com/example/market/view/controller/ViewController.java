package com.example.market.view.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
@RequiredArgsConstructor
public class ViewController {

    // 홈페이지
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    // 로그인
    @GetMapping("/sign-in")
    public String signIn() {
        return "sign-in";
    }


    // 회원가입
    @GetMapping("/sign-up")
    public String singUp() {
        return "sign-up";
    }


    @GetMapping("/my-page")
    public String myPage() {
        return "my-page";
    }
}
