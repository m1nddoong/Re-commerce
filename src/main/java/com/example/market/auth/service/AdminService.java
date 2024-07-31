package com.example.market.auth.service;

import com.example.market.auth.dto.UserDto;
import com.example.market.auth.entity.User;
import com.example.market.auth.repo.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    // 사업자 전환 신청 목록 확인 (관리자 전용)
    public List<UserDto> businessApplicationList() {
        List<User> userList = userRepository.findAllByBusinessApplyTrue();
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userList) {
            userDtoList.add(UserDto.fromEntity(user));
        }
        return userDtoList;
    }

    // 사업자 전환 신청 수락
    public UserDto businessApplcationApproval(UUID uuid) {
        User user = userRepository.findUserByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.setBusinessApply(true);
        return UserDto.fromEntity(userRepository.save(user));
    }

    public void businessApplcationRejection(UUID uuid) {
        userRepository.deleteUserByUuid(uuid);
    }
}
