package com.example.market.domain.auth.service;

import com.example.market.domain.auth.constant.Role;
import com.example.market.domain.auth.entity.User;
import com.example.market.domain.auth.dto.PrincipalDetails;
import com.example.market.domain.auth.constant.SocialType;
import com.example.market.domain.auth.dto.oauth2.GoogleResponse;
import com.example.market.domain.auth.repository.UserRepository;
import com.example.market.domain.auth.dto.oauth2.NaverResponse;
import com.example.market.domain.auth.dto.oauth2.OAuth2Response;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("PrincipalOAuth2UserService.loadUser() 실행 - OAuth2 로그인 요청 진입");
        // userRequest : 리소스 서버에서 받은 유저 정보
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // registrationId 가져오기 (naver, google 중 어디에서 온 요청인지)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null; // attributes
        if (registrationId.equals("naver")) {
            // 받은 oauth2User 변수에서 getAttribute 로 꺼내서 응답으로 넣어주기
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        // 로그인을 진행
        Optional<User> existUser = userRepository.findByEmail(oAuth2Response.getEmail());
        if (existUser.isPresent()) {
            log.info("{} 로그인을 한 적이 있습니다.", oAuth2Response.getProvider());
            User user = existUser.get();
            user.setUsername(oAuth2Response.getName());
            user.setProfileImg(oAuth2Response.getProfileUrl());
            userRepository.save(user);
            return new PrincipalDetails(existUser.get(), oAuth2User.getAttributes());
        }
        else {
            log.info("{} 로그인이 최초 입니다.", oAuth2Response.getProvider());
            // 유저 생성
            User newUser = userRepository.save(User.builder()
                    .email(oAuth2Response.getEmail())
                    .username(oAuth2Response.getName())
                    .socialType(SocialType.valueOf(registrationId))
                    .profileImg(oAuth2Response.getProfileUrl())
                    .role(Role.INACTIVE_USER)
                    .build());
            return new PrincipalDetails(newUser, oAuth2User.getAttributes());
        }
    }
}
