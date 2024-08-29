package com.example.market.domain.user.controller;

import com.example.market.domain.user.dto.BusinessDto;
import com.example.market.domain.user.dto.CreateUserDto;
import com.example.market.domain.user.dto.UpdateUserDto;
import com.example.market.domain.user.dto.UserDto;
import com.example.market.domain.user.dto.LoginRequestDto;
import com.example.market.domain.user.dto.JwtTokenDto;
import com.example.market.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Tag(name = "user", description = "사용자에 관한 API")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    @Operation(
            summary = "회원가입",
            description = "<p>'이메일', '비밀번호', '비밀번호 확인' 을 전달하여 회원가입이 가능합니다.</p>"
                    + "<p>최초 회원가입 시 <b>비활성 사용자</b> 로 가입됩니다.</p>"
                    + "<p>네 종류의 사용자가 있다 (비활성 사용자, 일반 사용자, 사업자 사용자, 관리자)</p>"
    )
    public ResponseEntity<UserDto> signUp(
            @RequestBody
            CreateUserDto dto
    ) {
        return ResponseEntity.ok(userService.signUp(dto));
    }


    @PostMapping("/sign-in")
    @Operation(
            summary = "로그인",
            description = "<p>사용자가 '이메일', '비밀번호' 를 입력하여 로그인 합니다. </p>"
                    + "<p>로그인을 하면 JWT(accessToken, refreshToken)를 발급됩니다. </p> "
    )
    public ResponseEntity<JwtTokenDto> signIn(
            @RequestBody
            LoginRequestDto dto,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(userService.signIn(dto, response));
    }


    @GetMapping("/sign-out")
    @Operation(
            summary = "로그아웃",
            description = "<p>현재 인증된 사용자는 로그이웃 합니다.</p>"
    )
    public ResponseEntity<String> signOut(
            @RequestHeader("Authentication")
            String accessToken
    ) {
        userService.signOut(accessToken);
        return ResponseEntity.ok("{}");
    }

    @PutMapping("/update-profile-info")
    @Operation(
            summary = "프로필 업데이트",
            description = "<p>사용자는 프로필 필수 정보 (닉네임, 성명, 나이, 전화번호)를 전달하여 자신의 프로필을 업데이트합니다. </p> "
                    + "<p> 프로필 필수 정보가 모두 작성 되었다면 자동으로 <b>일반 사용자</b>로 승급됩니다. </p>"
    )
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
    @Operation(
            summary = "프로필 이미지 업데이트",
            description = "<p>사용자는 자신의 프로필 이미지를 업로드할 수 있습니다.</p>"
    )
    public ResponseEntity<String> updateProfileImg(
            // 파일을 받아주는 자료형을 MutlipartFile
            @RequestParam("file")
            MultipartFile profileImg
    ) {
        return ResponseEntity.ok(userService.uploadProfileImage(profileImg));
    }

    @GetMapping("/my-profile")
    @Operation(
            summary = "프로필 확인",
            description = "<p>현재 인증된 사용자는 자신의 프로필을 확인할 수 있습니다.</p>"
    )
    public ResponseEntity<UserDto> myProfile() {
        return ResponseEntity.ok(userService.myProfile());
    }


    @PutMapping("/business-application")
    @Operation(
            summary = "사업자 사용자 전환 신청",
            description = "<p><b>일반 사용자</b> 는 자신의 사업자 등록번호(가정)을 전달해 사업자 사용자로 전환 신청할 수 있습니다.</p>"
    )
    public ResponseEntity<UserDto> businessApplication(
            @RequestBody
            BusinessDto dto
    ) {
        return ResponseEntity.ok(userService.businessApplication(dto));
    }

    @GetMapping("/business-application/list")
    @Operation(
            summary = "사업자 사용자 전환 신청 목록 조회",
            description = "<p><b>관리자</b> 사업자 사용자 전환 신청 목록을 확인할 수 있다.</p>"
    )
    public ResponseEntity<List<UserDto>> businessApplicationList() {
        return ResponseEntity.ok(userService.businessApplicationList());
    }


    @PutMapping("/business-application/{uuid}/approval")
    @Operation(
            summary = "사업자 사용자 전환 신청 수락",
            description = "<p><b>관리자</b> 사업자 사용자 전환 신청을 수락할 수 있다.</p>"
                    + "<p>일반 사용자가 사업자 사용자로 전환될 때 <b>준비중</b> 상태의 쇼핑몰이 추가된다.</p>"
                    + "<p>사업자 사용자는 이 쇼핑몰의 주인이 된다.</p>"
    )
    public ResponseEntity<UserDto> businessApplicationApproval(
            @PathVariable(value = "uuid")
            UUID uuid
    ) {
        return ResponseEntity.ok(userService.businessApplicationApproval(uuid));
    }

    @PutMapping("/business-application/{uuid}/rejection")
    @Operation(
            summary = "사업자 사용자 전환 신청 거절",
            description = "<p><b>관리자</b> 사업자 사용자 전환 신청을 거절할 수 있다.</p>"
                    + "<p>다른 회원가입 과정을 통해 만들어진 사용자는 관리자가 될 수 없다.</p>"
    )
    public ResponseEntity<Void> businessApplicationRejection(
            @PathVariable(value = "uuid")
            UUID uuid
    ) {
        userService.businessApplicationRejection(uuid);
        return ResponseEntity.ok().build();
    }
}





