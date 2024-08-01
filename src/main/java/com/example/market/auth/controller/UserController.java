package com.example.market.auth.controller;

import com.example.market.auth.dto.BusinessDto;
import com.example.market.auth.dto.CreateUserDto;
import com.example.market.auth.dto.UpdateUserDto;
import com.example.market.auth.dto.UserDto;
import com.example.market.auth.jwt.JwtRequestDto;
import com.example.market.auth.jwt.JwtResponseDto;
import com.example.market.auth.jwt.JwtTokenUtils;
import com.example.market.auth.service.UserService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import javax.print.attribute.standard.Media;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 회원가입
     *
     * @param dto 사용자의 username, password
     * @return 새로운 사용자를 생성 후 DB 저장
     */
    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> signUp(
            @RequestBody
            CreateUserDto dto
    ) {
        return ResponseEntity.ok(userService.signUp(dto));
    }

    /**
     * 로그인(JWT 토큰 발급)
     *
     * @param dto usenname, password
     * @return token
     */
    @PostMapping("/sign-in")
    public JwtResponseDto signIn(
            @RequestBody
            JwtRequestDto dto
    ) {
        return userService.signIn(dto);
    }


    /**
     * 마이 프로필 확인
     */
    @GetMapping("/my-profile")
    public UserDto myProfile() {
        return userService.myProfile();
    }

    /**
     * 프로필 필수 정보 기입
     *
     * @param dto 성명, 닉네임, 나이, 전화번호
     */
    @PutMapping("/update-profile-info")
    public ResponseEntity<UserDto> updateProfile(
            @RequestBody
            UpdateUserDto dto
    ) {
        return ResponseEntity.ok(userService.updateProfile(dto));
    }


    /**
     * 프로필 img 업로드
     */
    @PutMapping(
            value = "/update-profile-img",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> updateProfileImg(
            // 파일을 받아주는 자료형을 MutlipartFile
            @RequestParam("file")
            MultipartFile profileImg
    ) {
        return ResponseEntity.ok(userService.uploadProfileImage(profileImg));
    }

    /**
     * 사업자 등록 번호 신청
     *
     * @param dto 사업자 등록 번호
     */
    @PutMapping("/business-application")
    public ResponseEntity<UserDto> businessApplication(
            @RequestBody
            BusinessDto dto
    ) {
        return ResponseEntity.ok(userService.businessApplication(dto));
    }

}





