package com.example.market.auth.service;

import com.example.market.auth.controller.AuthenticationFacade;
import com.example.market.auth.dto.CreateUserDto;
import com.example.market.auth.dto.UserDto;
import com.example.market.auth.entity.CustomUserDetails;
import com.example.market.auth.entity.Role;
import com.example.market.auth.entity.User;
import com.example.market.auth.jwt.JwtRequestDto;
import com.example.market.auth.jwt.JwtResponseDto;
import com.example.market.auth.jwt.JwtTokenUtils;
import com.example.market.auth.repo.RoleRepository;
import com.example.market.auth.repo.UserRepository;
import com.example.market.common.util.AppConstants;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationFacade authenticationFacade;


    @Override
    // 원래는 username 을 이용해 사용자 정보를 조회하지만 -> uuid 를 사용
    public UserDetails loadUserByUsername(String uuid) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByUuid(UUID.fromString(uuid));
        if (optionalUser.isEmpty()) throw new UsernameNotFoundException("uuid no found");
        User user = optionalUser.get();

        // 조회된 사용자 정보를 바탕으로 CustomUserDetails 로 만들기
        return CustomUserDetails.builder()
                .user(user)
                .build();

    }


    /**
     * 회원 가입
     *
     * @param dto 회원가입 정보
     */
    @Transactional
    public UserDto signUp(
            CreateUserDto dto
    ) {
        if (!dto.getPassword().equals(dto.getPasswordCheck())) {
            // 비밀번호, 비밀번호 확인이 일치하지 않음
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            // 동일한 이메일이 이미 존재
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        String uuid = UUID.randomUUID().toString();
        // 최초 회원가입 시 "INACTIVE_USER" 로 등록됨
        Optional<Role> optionalRole = roleRepository.findById(1L);
        if (optionalRole.isEmpty()) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        return UserDto.fromEntity(userRepository.save(User.builder()
                .uuid(UUID.fromString(uuid))
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .username(dto.getUsername())
                .age(dto.getAge())
                .phone(dto.getPhone())
                .profileImg(dto.getProfileImg())
                .role(optionalRole.get())
                .build()));
    }


    public JwtResponseDto signIn(
            JwtRequestDto dto
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
        String accessToken = jwtTokenUtils.generateToken(user);

        return JwtResponseDto.builder()
                .uuid(String.valueOf(user.getUuid()))
                .accessToken(accessToken)
                .expiredDate(LocalDateTime.now().plusSeconds(AppConstants.ACCESS_TOKEN_EXPIRE_SECOND))
                .expiredSecond(AppConstants.ACCESS_TOKEN_EXPIRE_SECOND)
                .build();
    }

    public UserDto myProfile() {
        // 일단 사용자 확인
        User user = authenticationFacade.extractUser();
        return UserDto.fromEntity(user);
    }

}
