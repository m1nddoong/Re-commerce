package com.example.market.auth.service;

import com.example.market.auth.dto.UserDto;
import com.example.market.auth.entity.User;
import com.example.market.auth.entity.User.BusinessStatus;
import com.example.market.auth.entity.User.Role;
import com.example.market.auth.repo.UserRepository;
import com.example.market.shop.entity.Shop;
import com.example.market.shop.repo.ShopRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;

    /**
     * 사업자 전환 신청 목록 확인 (관리자 전용)
     * @return 신청 목록
     */
    public List<UserDto> businessApplicationList() {
        List<User> userList = userRepository.findAllByBusinessStatus(BusinessStatus.APPLIED);
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userList) {
            userDtoList.add(UserDto.fromEntity(user));
        }
        return userDtoList;
    }

    /**
     * 사업자 전환 신청 수락 -> 사업자 사용자로 전환
     * @param uuid 사용자 uuid
     * @return 사업자 전환 수락된 사용자
     */
    public UserDto businessApplicationApproval(UUID uuid) {
        User user = userRepository.findUserByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.setBusinessStatus(BusinessStatus.APPROVED);
        user.setAuthorities(Role.BUSINESS_USER.getRoles());

        Shop newShop = new Shop();
        newShop.setUser(user);
        shopRepository.save(newShop); // 쇼핑몰 개설
        return UserDto.fromEntity(userRepository.save(user));
    }

    /**
     * 사업자 전환 신청 거절
     * @param uuid 사용자 uuid
     */
    public void businessApplicationRejection(UUID uuid) {
        User user = userRepository.findUserByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.setBusinessStatus(BusinessStatus.REJECTED);
        userRepository.save(user);
    }
}
