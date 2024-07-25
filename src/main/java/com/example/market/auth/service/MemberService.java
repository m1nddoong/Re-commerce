package com.example.market.auth.service;

import com.example.market.auth.dto.CreateMemberDto;
import com.example.market.auth.dto.MemberDto;
import com.example.market.auth.entity.CustomMemberDetails;
import com.example.market.auth.entity.Member;
import com.example.market.auth.jwt.JwtRequestDto;
import com.example.market.auth.jwt.JwtResponseDto;
import com.example.market.auth.jwt.JwtTokenUtils;
import com.example.market.auth.repo.MemberRepository;
import jakarta.transaction.Transactional;
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
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }


    // createUser 와 동일
    @Transactional
    public MemberDto signUp(
            CreateMemberDto dto
    ) {
        if (!dto.getPassword().equals(dto.getPasswordCheck())) {
            // 비밀번호, 비밀번호 확인이 일치하지 않음
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (memberRepository.existsByEmail(dto.getEmail())) {
            // 동일한 이메일이 이미 존재
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return MemberDto.fromEntity(memberRepository.save(Member.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .username(dto.getUsername())
                .age(dto.getAge())
                .phone(dto.getPhone())
                .build()));
    }


    public JwtResponseDto signIn(
            JwtRequestDto dto
    ) {
        Member member = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        if (!passwordEncoder.matches(
                dto.getPassword(),
                member.getPassword()
        )) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        // JWT 발급
        String jwt = jwtTokenUtils.generateToken(CustomMemberDetails.fromEntity(member));
        JwtResponseDto response = new JwtResponseDto();
        response.setToken(jwt);
        return response;
    }

}
