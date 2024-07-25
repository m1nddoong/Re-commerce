package com.example.market.auth.controller;

import com.example.market.auth.dto.CreateMemberDto;
import com.example.market.auth.dto.MemberDto;
import com.example.market.auth.jwt.JwtRequestDto;
import com.example.market.auth.jwt.JwtResponseDto;
import com.example.market.auth.jwt.JwtTokenUtils;
import com.example.market.auth.service.MemberService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;

    /**
     * 회원가입
     * @param dto 사용자의 username, password
     * @return 새로운 사용자를 생성 후 DB 저장
     */
    @PostMapping("/sign-up")
    public ResponseEntity<MemberDto> signUp(
            @RequestBody
            CreateMemberDto dto
    ) {
        return ResponseEntity.ok(memberService.signUp(dto));
    }

    /**
     * 로그인(JWT 토큰 발급)
     * @param dto usenname, password
     * @return token
     */
    @PostMapping("/sign-in")
    public JwtResponseDto signIn(
            @RequestBody
            JwtRequestDto dto
    ) {
        return memberService.signIn(dto);
    }






    @GetMapping("/validate")
    public Claims validateToken(
            @RequestParam("token")
            String token
    ) {
        if (!jwtTokenUtils.validate(token)) {
            log.info("토큰이 유효하지 않음");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }


        return jwtTokenUtils.parseClaims(token);
    }
}
