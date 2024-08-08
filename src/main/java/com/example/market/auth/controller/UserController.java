package com.example.market.auth.controller;

import com.example.market.auth.dto.BusinessDto;
import com.example.market.auth.dto.CreateUserDto;
import com.example.market.auth.dto.UpdateUserDto;
import com.example.market.auth.dto.UserDto;
import com.example.market.auth.dto.LoginRequestDto;
import com.example.market.auth.dto.JwtTokenDto;
import com.example.market.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> signUp(
            @RequestBody
            CreateUserDto dto
    ) {
        return ResponseEntity.ok(userService.signUp(dto));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtTokenDto> signIn(
            @RequestBody
            LoginRequestDto dto
    ) {
        return ResponseEntity.ok(userService.signIn(dto));
    }

    @GetMapping("/sign-out")
    public ResponseEntity<String> signOut(
            @RequestHeader("Authentication")
            String accessToken
    ) {
        userService.signOut(accessToken);
        return ResponseEntity.ok("{}");
    }

    @GetMapping("/my-profile")
    public ResponseEntity<UserDto> myProfile() {
        return ResponseEntity.ok(userService.myProfile());
    }

    @PutMapping("/update-profile-info")
    public ResponseEntity<UserDto> updateProfile(
            @RequestBody
            UpdateUserDto dto
    ) {
        return ResponseEntity.ok(userService.updateProfile(dto));
    }

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

    @PutMapping("/business-application")
    public ResponseEntity<UserDto> businessApplication(
            @RequestBody
            BusinessDto dto
    ) {
        return ResponseEntity.ok(userService.businessApplication(dto));
    }

}





