package com.example.market.auth.service;

import com.example.market.auth.entity.CustomUserDetails;
import com.example.market.auth.entity.User;
import com.example.market.auth.jwt.TokenType;
import com.example.market.common.util.AuthenticationFacade;
import com.example.market.auth.dto.BusinessDto;
import com.example.market.auth.dto.CreateUserDto;
import com.example.market.auth.dto.UpdateUserDto;
import com.example.market.auth.dto.UserDto;
import com.example.market.auth.entity.Role;
import com.example.market.auth.dto.LoginRequestDto;
import com.example.market.auth.dto.JwtTokenDto;
import com.example.market.auth.jwt.JwtTokenUtils;
import com.example.market.auth.repo.UserRepository;
import com.example.market.common.util.FileHandlerUtils;
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호 확인이 일치하지 않음");
        }
        // 동일한 이메일이 이미 존재하는지
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이메일 중복");
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
     * 로그인
     *
     * @param dto (토큰 정보)
     */
    public JwtTokenDto signIn(
            LoginRequestDto dto
    ) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        // 입력한 비밀번호 체크
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (!passwordEncoder.matches(
                dto.getPassword(),
                user.getPassword()
        )) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        // JWT 토큰 발급
        // 원래 dto 를 바탕으로 CustomUseDetails 를 만들어서 generateToken 메서드를 호출해줬는데
        // CustomUserDetails 로 만들어주기 전에 일단 토큰 발급?
        return JwtTokenDto.builder()
                .uuid(String.valueOf(user.getUuid()))
                .accessToken(jwtTokenUtils.generateToken(user, TokenType.ACCESS))
                .refreshToken(jwtTokenUtils.generateToken(user, TokenType.ACCESS))
                // 토큰 만료 날짜
                .expiredDate(LocalDateTime.now().plusSeconds(TokenType.ACCESS.getTokenValidMillis()))
                // 토큰 만료 시간 (초단위)
                .expiredSecond(TokenType.ACCESS.getTokenValidMillis() / 1000)
                .build();
    }

    /**
     * 프로필 필수 정보 기입 -> 활성 사용자로 승급
     * @param dto
     * @return
     */
    public UserDto updateProfile(
            UpdateUserDto dto
    ) {
        User currentUser = authenticationFacade.extractUser();

        currentUser.setUsername(dto.getUsername());
        currentUser.setNickname(dto.getNickname());
        currentUser.setAge(dto.getAge());
        currentUser.setPhone(dto.getPhone());

        // 모든 필드가 null이 아닌지 확인
        boolean allFieldsNotNull = Stream.of(dto.getUsername(), dto.getNickname(), dto.getAge(), dto.getPhone())
                .allMatch(Objects::nonNull);

        if (allFieldsNotNull) {
            currentUser.setAuthorities(Role.ACTIVE_USER.getRoles());
        }

        return UserDto.fromEntity(userRepository.save(currentUser));
    }

    /**
     * 마이 프로필
     * @return
     */

    public UserDto myProfile() {
        User user = authenticationFacade.extractUser();
        return UserDto.fromEntity(user);
    }

    /**
     * 프로필 이미지 업로드
     * @param profileImg
     * @return
     */
    public String uploadProfileImage(MultipartFile profileImg) {
        User currentUser = authenticationFacade.extractUser();
        // 기존 이미지 삭제
        String oldProfile = currentUser.getProfileImg();
        if (oldProfile != null) fileHandlerUtils.deleteImage(oldProfile);

        String imagePath = fileHandlerUtils.saveImage(profileImg);
        imagePath = imagePath.replaceAll("\\\\", "/");
        currentUser.setProfileImg(imagePath);

        userRepository.save(currentUser);
        return "done";
    }

    // 사업자 전환 신청
    public UserDto businessApplication(BusinessDto dto) {
        User user = authenticationFacade.extractUser();
        user.setBusinessNum(dto.getBusinessNum());
        return UserDto.fromEntity(userRepository.save(user));
    }
}
