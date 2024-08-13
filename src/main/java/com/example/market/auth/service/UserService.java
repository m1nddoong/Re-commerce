package com.example.market.auth.service;

import com.example.market.auth.entity.CustomUserDetails;
import com.example.market.auth.entity.RefreshToken;
import com.example.market.auth.entity.User;
import com.example.market.auth.entity.User.Role;
import com.example.market.auth.jwt.TokenType;
import com.example.market.auth.repo.RefreshTokenRepository;
import com.example.market.common.util.AuthenticationFacade;
import com.example.market.auth.dto.BusinessDto;
import com.example.market.auth.dto.CreateUserDto;
import com.example.market.auth.dto.UpdateUserDto;
import com.example.market.auth.dto.UserDto;
import com.example.market.auth.dto.LoginRequestDto;
import com.example.market.auth.dto.JwtTokenDto;
import com.example.market.auth.jwt.JwtTokenUtils;
import com.example.market.auth.repo.UserRepository;
import com.example.market.common.util.FileHandlerUtils;
import com.example.market.shop.entity.Shop;
import com.example.market.shop.repo.ShopRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationFacade authenticationFacade;
    private final FileHandlerUtils fileHandlerUtils;
    private final RefreshTokenRepository refreshTokenRepository;


    @Override
    // 원래는 username 을 이용해 사용자 정보를 조회하지만 -> uuid 를 사용
    public UserDetails loadUserByUsername(String uuid) {
        try {
            UUID userUuid = UUID.fromString(uuid);
            User user = userRepository.findUserByUuid(userUuid)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            // 조회된 사용자 정보를 바탕으로 CustomUserDetails 로 만들기
            return CustomUserDetails.builder()
                    .user(user)
                    .build();
        } catch (IllegalArgumentException e) {
            throw new UsernameNotFoundException("Invalid UUID format");
        }

    }

    /**
     * 회원 가입
     *
     * @param dto 이메일, 비밀번호, 비밀번호 확인
     */
    @Transactional
    public UserDto signUp(
            CreateUserDto dto
    ) {
        // 비밀번호, 비밀번호 확인이 일치하지 않는지
        if (!dto.getPassword().equals(dto.getPasswordCheck())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        // 동일한 이메일이 이미 존재하는지
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        String uuid = UUID.randomUUID().toString();
        return UserDto.fromEntity(userRepository.save(User.builder()
                .uuid(UUID.fromString(uuid))
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .authorities(Role.INACTIVE_USER.getRoles())
                .build()));
    }

    /**
     * 로그인 (Access Token 발급)
     *
     * @param dto 이메일, 비밀번호
     * @return accessToken
     */
    public JwtTokenDto signIn(
            LoginRequestDto dto
    ) {
        // 사용자 존재 확인
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        // 비밀번호 체크
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        // accessToken, refreshToken 생성 후 redis에 uuid, accessToken, refreshToken 저장
        String newAccessToken = jwtTokenUtils.generateToken(user, TokenType.ACCESS);
        String newRefreshToken = jwtTokenUtils.generateToken(user, TokenType.REFRESH);

        refreshTokenRepository.save(RefreshToken.builder()
                .uuid(String.valueOf(user.getUuid()))
                .refreshToken(newRefreshToken)
                .build());

        RefreshToken test = refreshTokenRepository.findById(String.valueOf(user.getUuid()))
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "refreshToken 조회 실패"));

        log.info("저장된 RefreshToken 정보: {}", test.toString());
        log.info("uuid: {}", test.getUuid());
        log.info("refreshToken: {}", test.getRefreshToken());



        // JWT 토큰 발급 -> 이후 JwtTokenFilter 에서 유효성 검증 후 인증 정보 저장
        return JwtTokenDto.builder()
                .uuid(String.valueOf(user.getUuid()))
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .expiredDate(LocalDateTime.now().plusSeconds(TokenType.ACCESS.getTokenValidMillis() / 1000))
                .expiredSecond(TokenType.ACCESS.getTokenValidMillis() / 1000)
                .build();
    }

    /**
     * 로그아웃 (redis 에서 token 정보 삭제)
     *
     * @param uuid 사용자 uuid
     */
    public void signOut(String uuid) {
        refreshTokenRepository.delete(refreshTokenRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }


    /**
     * 프로필 필수 정보 기입 -> 활성 사용자로 승급
     *
     * @param dto 프로필 업데이트 정보
     */
    public UserDto updateProfile(
            UpdateUserDto dto
    ) {
        User currentUser = authenticationFacade.extractUser();
        currentUser.setUsername(dto.getUsername());
        currentUser.setNickname(dto.getNickname());
        currentUser.setAge(dto.getAge());
        currentUser.setPhone(dto.getPhone());
        currentUser.setAuthorities(Role.ACTIVE_USER.getRoles());

        return UserDto.fromEntity(userRepository.save(currentUser));
    }

    /**
     * 마이 프로필 조회
     */
    public UserDto myProfile() {
        return UserDto.fromEntity(authenticationFacade.extractUser());
    }

    /**
     * 프로필 이미지 업로드
     *
     * @param profileImg 프로필 이미지
     */
    public String uploadProfileImage(MultipartFile profileImg) {
        User currentUser = authenticationFacade.extractUser();
        // 기존 이미지 삭제
        String oldProfile = currentUser.getProfileImg();
        if (oldProfile != null) {
            fileHandlerUtils.deleteImage(oldProfile);
        }

        String imagePath = fileHandlerUtils.saveImage(profileImg);
        imagePath = imagePath.replaceAll("\\\\", "/");
        currentUser.setProfileImg(imagePath);

        userRepository.save(currentUser);
        return "done";
    }

    /**
     * 사업자 전환 신청
     * @param dto 사업자 등록 번호
     */
    public UserDto businessApplication(BusinessDto dto) {
        User user = authenticationFacade.extractUser();
        user.setBusinessNum(dto.getBusinessNum());
        return UserDto.fromEntity(userRepository.save(user));
    }


}
