package com.example.market.auth.controller;

import com.example.market.auth.dto.CreateUserDto;
import com.example.market.auth.dto.UpdateUserDto;
import com.example.market.auth.dto.UserDto;
import com.example.market.auth.jwt.JwtRequestDto;
import com.example.market.auth.jwt.JwtResponseDto;
import com.example.market.auth.jwt.JwtTokenUtils;
import com.example.market.auth.service.UserService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;

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
     * 사용자 프로필 업데이트
     *
     * @param dto 성명, 닉네임, 나이, 전화번호
     */
    @PutMapping("/update-profile")
    public ResponseEntity<UserDto> updateProfile(
            @RequestBody
            UpdateUserDto dto
    ) {
        return ResponseEntity.ok(userService.updateProfile(dto));
    }

    /**
     * 마이 프로필 확인
     */
    @GetMapping("/my-profile")
    public UserDto myProfile() {
        return userService.myProfile();
    }


    /**
     * 프로필 img 업로드
     */
    @PutMapping(
            value = "multipart",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public String multipart(
            @RequestParam("name")
            String name,
            // 파일을 받아주는 자료형을 MutlipartFile
            @RequestParam("file")
            MultipartFile multipartFile
    ) throws IOException {
        // 저장할 경로 생성
        Files.createDirectories(Path.of("media"));
        // 저장할 파일 이름을 경로를 포함해 지정
        Path downloadPath = Path.of("media/" + multipartFile.getOriginalFilename());
        // 저장
        multipartFile.transferTo(downloadPath);

        return "http://location:8080/static" + multipartFile.getOriginalFilename();
    }
}




