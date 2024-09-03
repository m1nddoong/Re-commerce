package com.example.market.domain.auth.service;

import com.example.market.domain.auth.constant.BusinessStatus;
import com.example.market.domain.auth.constant.Role;
import com.example.market.domain.auth.entity.RefreshToken;
import com.example.market.domain.auth.entity.User;
import com.example.market.domain.auth.jwt.TokenType;
import com.example.market.domain.auth.repository.RefreshTokenRepository;
import com.example.market.domain.auth.dto.BusinessDto;
import com.example.market.domain.auth.dto.CreateUserDto;
import com.example.market.domain.auth.dto.UpdateUserDto;
import com.example.market.domain.auth.dto.UserDto;
import com.example.market.domain.auth.dto.LoginDto;
import com.example.market.domain.auth.jwt.JwtTokenDto;
import com.example.market.domain.auth.jwt.JwtTokenUtils;
import com.example.market.domain.auth.repository.UserRepository;
import com.example.market.domain.auth.dto.PrincipalDetails;
import com.example.market.global.error.exception.ErrorCode;
import com.example.market.global.error.exception.GlobalCustomException;
import com.example.market.global.util.CookieUtil;
import com.example.market.global.util.FileHandlerUtils;
import com.example.market.domain.shop.entity.Shop;
import com.example.market.domain.shop.repository.ShopRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final CookieUtil cookieUtil;
    private final AuthenticationFacade authenticationFacade;
    private final FileHandlerUtils fileHandlerUtils;
    private final RefreshTokenRepository refreshTokenRepository;



    /*
    글 잘 읽었습니다 :) 이전 포스트에서 JWTFilter를 구현하신 부분에서 UserDetails에 회원 정보를 담교 세션에 사용자를 등록하셨잖아요. 그런데 OAuth2로 로그인한 사용자도
     */

    @Override
    // 원래는 username 을 이용해 사용자 정보를 조회하지만 email 사용
    public PrincipalDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalCustomException(ErrorCode.USER_NOT_FOUND));
        // 조회된 사용자 정보를 바탕으로 CustomUserDetails 로 만들기
        return new PrincipalDetails(user);
    }

    /**
     * 회원 가입
     */
    @Transactional
    public UserDto signUp(
            CreateUserDto dto,
            MultipartFile profileImg
    ) {
        // 비밀번호, 이메일 체크
        if (!dto.getPassword().equals(dto.getPasswordCheck())) {
            throw new GlobalCustomException(ErrorCode.LOGIN_PASSWORD_CHECK_NOT_MATCH);
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new GlobalCustomException(ErrorCode.LOGIN_DUPLICATED_EMAIL);
        }

        Role role = Role.INACTIVE_USER;
        // 만약 닉네임, 성명, 나이, 전화번호가 모두 전달되었다면 role 을 Role.ACTIVE 로 변경
        if (dto.getUsername() != null && !dto.getUsername().isEmpty() &&
                dto.getNickname() != null && !dto.getNickname().isEmpty() &&
                dto.getBirthday() != null &&
                dto.getPhone() != null && !dto.getPhone().isEmpty()
        ) {
            role = Role.ACTIVE_USER;
        }

        // 사용자 생성
        return UserDto.fromEntity(userRepository.save(User.builder()
                .uuid(UUID.randomUUID())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .username(dto.getUsername())
                .phone(dto.getPhone())
                .nickname(dto.getNickname())
                .birthday(dto.getBirthday())
                .profileImg(fileHandlerUtils.saveImage(profileImg))
                .role(role)
                .build()));
    }

    /**
     * 로그인
     */
    public JwtTokenDto signIn(
            LoginDto dto,
            HttpServletResponse response
    ) {
        // 사용자 존재 확인
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        // 비밀번호 체크
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        // accessToken, refreshToken 생성
        String newAccessToken = jwtTokenUtils.createJwt(user.getEmail(), TokenType.ACCESS);
        String newRefreshToken = jwtTokenUtils.createJwt(user.getEmail(), TokenType.REFRESH);

        // redis에 uuid, accessToken, refreshToken 저장
        refreshTokenRepository.save(RefreshToken.builder()
                .uuid(String.valueOf(user.getUuid()))
                .refreshToken(newRefreshToken)
                .build());

        // 쿠키 생성
        response.addCookie(cookieUtil.createCookie("Authorization", newAccessToken));

        // JWT 토큰 발급 -> 이후 JwtTokenFilter 에서 유효성 검증 후 인증 정보 저장
        return JwtTokenDto.builder()
                .uuid(String.valueOf(user.getUuid()))
                .accessToken(newAccessToken)
                .expiredDate(LocalDateTime.now().plusSeconds(TokenType.ACCESS.getTokenValidMillis() / 1000))
                .expiredSecond(TokenType.ACCESS.getTokenValidMillis() / 1000)
                .build();
    }

    /**
     * 로그아웃 (redis 에서 token 정보 삭제)
     */
    public void signOut(HttpServletResponse response) {
        // 현재 인증된 사용자의 uuid 조회
        String uuid = String.valueOf(authenticationFacade.extractUser().getUuid());
        // Redis 에서 refreshToken 삭제
        refreshTokenRepository.findById(uuid)
                .ifPresentOrElse(
                        refreshTokenRepository::delete,
                        () -> {
                            throw new GlobalCustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
                        }
                );
        // 쿠키 삭제
        Cookie cookie = cookieUtil.deleteCookie("Authorization");
        response.addCookie(cookie);
    }

    /**
     * 프로필 조회
     */
    public UserDto myProfile(PrincipalDetails principalDetails) {
        return UserDto.fromEntity(principalDetails.getUser());
    }


    /**
     * 프로필 업데이트
     */
    public UserDto updateProfile(
            UpdateUserDto dto,
            MultipartFile profileImg
    ) {
        User currentUser = authenticationFacade.extractUser();

        // 기존 이미지 삭제
        String oldProfile = currentUser.getProfileImg();
        if (oldProfile != null) {
            fileHandlerUtils.deleteImage(oldProfile);
        }

        currentUser.setUsername(dto.getUsername());
        currentUser.setPhone(dto.getPhone());
        currentUser.setNickname(dto.getNickname());
        currentUser.setBirthday(dto.getBirthday());
        currentUser.setProfileImg(fileHandlerUtils.saveImage(profileImg));
        currentUser.setRole(Role.ACTIVE_USER);
        return UserDto.fromEntity(userRepository.save(currentUser));
    }


    /**
     * 사업자 전환 신청
     *
     * @param dto 사업자 등록 번호
     */
    public UserDto applyForBusinessUpgrade(BusinessDto dto) {
        User currentUser = authenticationFacade.extractUser();
        currentUser.setBusinessNum(dto.getBusinessNum());
        return UserDto.fromEntity(userRepository.save(currentUser));
    }

    /**
     * 사업자 전환 신청 목록 확인 (관리자 전용)
     *
     * @return 신청 목록
     */
    public List<UserDto> listBusinessRequests() {
        List<User> userList = userRepository.findAllByBusinessStatus(BusinessStatus.APPLIED);
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userList) {
            userDtoList.add(UserDto.fromEntity(user));
        }
        return userDtoList;
    }

    /**
     * 사업자 전환 신청 수락 -> 사업자 사용자로 전환
     *
     * @param uuid 사용자 uuid
     * @return 사업자 전환 수락된 사용자
     */
    public UserDto approveBusinessRequest(UUID uuid) {
        User user = userRepository.findUserByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.setBusinessStatus(BusinessStatus.APPROVED);
        user.setRole(Role.BUSINESS_USER);

        Shop newShop = new Shop();
        newShop.setUser(user);
        shopRepository.save(newShop); // 쇼핑몰 개설
        return UserDto.fromEntity(userRepository.save(user));
    }

    /**
     * 사업자 전환 신청 거절
     *
     * @param uuid 사용자 uuid
     */
    public UserDto rejectBusinessRequest(UUID uuid) {
        User user = userRepository.findUserByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.setBusinessStatus(BusinessStatus.REJECTED);
        return UserDto.fromEntity(userRepository.save(user));
    }
}
