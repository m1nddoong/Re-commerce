package com.example.market.domain.auth.controller;

import com.example.market.domain.auth.dto.BusinessDto;
import com.example.market.domain.auth.dto.CreateUserDto;
import com.example.market.domain.auth.dto.UpdateUserDto;
import com.example.market.domain.auth.dto.UserDto;
import com.example.market.domain.auth.dto.LoginDto;
import com.example.market.domain.auth.dto.PrincipalDetails;
import com.example.market.domain.auth.service.PrincipalDetailsService;
import com.example.market.domain.auth.jwt.JwtTokenDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Tag(name = "auth", description = "사용자 인증 관련 API")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {
    private final PrincipalDetailsService principalDetailsService;

    @PostMapping(value = "/sign-up", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "회원가입",
            description = "<p>기본적으로 '이메일', '비밀번호', '비밀번호 확인' 을 전달하여 회원가입이 가능합니다.</p>"
                    + "<p>사용자는 '비활성 사용자, 일반 사용자, 사업자 사용자, 관리자' 가 있으며 최초 회원가입 시 <b>비활성 사용자</b> 로 가입됩니다.</p>"
                    + "<p>프로필 추가 정보 (닉네임, 성명, 나이, 전화번호, 프로필 이미지)를 추가로 전달할 수 있습니다.</p> "
                    + "<p>이때, 프로필 필수 정보(닉네임, 성명, 나이, 전화번호)가 모두 작성 되었다면 <b>일반 사용자</b>로 승급됩니다.</p>"
    )
    public ResponseEntity<UserDto> signUp(
            @Valid @RequestPart(value = "createDto")
            CreateUserDto dto,
            @RequestPart(value = "profileImg", required = false)
            MultipartFile profileImg

    ) {
        return ResponseEntity.ok(principalDetailsService.signUp(dto, profileImg));
    }


    @PostMapping("/sign-in")
    @ApiResponse()
    @Operation(
            summary = "로그인",
            description = "<p>사용자가 '이메일', '비밀번호' 를 입력하여 로그인 합니다. </p>"
                    + "<p>로그인을 하면 JWT(accessToken, refreshToken)를 발급됩니다. </p> "
    )
    public ResponseEntity<JwtTokenDto> signIn(
            @RequestBody
            LoginDto dto,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(principalDetailsService.signIn(dto, response));
    }


    @PostMapping("/sign-out")
    @Operation(
            summary = "로그아웃",
            description = "<p>현재 인증된 사용자는 로그이웃 합니다.</p>"
    )
    public ResponseEntity<String> signOut(
            HttpServletResponse response
    ) {
        principalDetailsService.signOut(response);
        return ResponseEntity.ok("로그아웃 성공!");
    }

    @GetMapping("/profile")
    @Operation(
            summary = "프로필 조회",
            description = "<p>현재 인증된 사용자는 자신의 프로필을 확인할 수 있습니다.</p>"
    )
    public ResponseEntity<UserDto> myProfile(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        return ResponseEntity.ok(principalDetailsService.myProfile(principalDetails));
    }


    @PutMapping(value = "/profile/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "프로필 업데이트",
            description = "<p>사용자는 프로필 추가 정보 (닉네임, 성명, 나이, 전화번호, 프로필 이미지)를 전달하여 자신의 프로필을 업데이트합니다. </p>"
                    + "<p>마찬가지로 프로필 필수 정보 (닉네임, 성명, 나이, 전화번호) 를 입력하면 <b>일반 사용자</b> 로 승급됩니다. </p>"
    )
    public ResponseEntity<UserDto> updateProfile(
            @Valid @RequestPart(value = "updateDto")
            UpdateUserDto dto,
            @RequestPart(value = "profileImg")
            MultipartFile profileImg
    ) {
        return ResponseEntity.ok(principalDetailsService.updateProfile(dto, profileImg));
    }

    @PostMapping("/token/refresh")
    @Operation(
            summary = "accessToken 재발급",
            description = "<p>현재 인증된 사용자의 uuid 부터 redis 에 저장된 accessToken 을 찾고,</p>"
                    + "<p>accessToken, refreshToken 재발급</p>"
    )
    public ResponseEntity<JwtTokenDto> refreshJwtToken(HttpServletResponse response) {
        return ResponseEntity.ok(principalDetailsService.refreshJwtToken(response));
    }

    @PutMapping("/business-apply")
    @Operation(
            summary = "사업자 사용자 전환 신청",
            description = "<p><b>일반 사용자</b> 는 자신의 사업자 등록번호(가정)을 전달해 사업자 사용자로 전환 신청할 수 있습니다.</p>"
    )
    public ResponseEntity<UserDto> applyForBusinessUpdate(
            @RequestBody
            BusinessDto dto
    ) {
        return ResponseEntity.ok(principalDetailsService.applyForBusinessUpgrade(dto));
    }

    @GetMapping("/business-requests")
    @Operation(
            summary = "사업자 사용자 전환 신청 목록 조회",
            description = "<p><b>관리자</b> 사업자 사용자 전환 신청 목록을 확인할 수 있다.</p>"
    )
    public ResponseEntity<List<UserDto>> listBusinessRequests() {
        return ResponseEntity.ok(principalDetailsService.listBusinessRequests());
    }


    @PutMapping("/business-requests/{uuid}/approve")
    @Operation(
            summary = "사업자 사용자 전환 신청 수락",
            description = "<p><b>관리자</b> 사업자 사용자 전환 신청을 수락할 수 있다.</p>"
                    + "<p>일반 사용자가 사업자 사용자로 전환될 때 <b>준비중</b> 상태의 쇼핑몰이 추가된다.</p>"
                    + "<p>사업자 사용자는 이 쇼핑몰의 주인이 된다.</p>"
    )
    public ResponseEntity<UserDto> approveBusinessRequest(
            @PathVariable(value = "uuid")
            UUID uuid
    ) {
        return ResponseEntity.ok(principalDetailsService.approveBusinessRequest(uuid));
    }

    @PutMapping("/business-requests/{uuid}/reject")
    @Operation(
            summary = "사업자 사용자 전환 신청 거절",
            description = "<p><b>관리자</b> 사업자 사용자 전환 신청을 거절할 수 있다.</p>"
                    + "<p>다른 회원가입 과정을 통해 만들어진 사용자는 관리자가 될 수 없다.</p>"
    )
    public ResponseEntity<UserDto> rejectBusinessRequest(
            @PathVariable(value = "uuid")
            UUID uuid
    ) {
        return ResponseEntity.ok(principalDetailsService.rejectBusinessRequest(uuid));
    }
}





