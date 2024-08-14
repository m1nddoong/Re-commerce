package com.example.market.shop.service;

import com.example.market.auth.entity.User;
import com.example.market.common.exception.CustomGlobalErrorCode;
import com.example.market.common.exception.GlobalExceptionHandler;
import com.example.market.common.util.AuthenticationFacade;
import com.example.market.shop.constant.ShopCategory;
import com.example.market.shop.constant.ShopStatus;
import com.example.market.shop.dto.ShopDto;
import com.example.market.shop.dto.UpdateShopDto;
import com.example.market.shop.entity.Shop;
import com.example.market.shop.repo.ShopRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;
    private final AuthenticationFacade authFacade;

    /**
     * 쇼핑몰 정보 업데이트
     * @param dto 쇼핑몰 이름, 소개, 분류
     * @return 쇼핑몰 정보
     */
    public ShopDto updateShop(
            UpdateShopDto dto
    ) {
        // 현재 사용자의 쇼핑몰 가져오기
        User currentUser = authFacade.extractUser();
        Shop targetShop = shopRepository.findShopByUserId(currentUser.getId())
                .orElseThrow(() -> new GlobalExceptionHandler(CustomGlobalErrorCode.SHOP_NOT_EXISTS));
        // 쇼핑몰 수정
        targetShop.setName(dto.getName());
        targetShop.setIntroduction(dto.getIntroduction());
        targetShop.setCategory(dto.getCategory());
        shopRepository.save(targetShop);
        return ShopDto.fromEntity(targetShop);
    }

    /**
     * 쇼핑몰 개설 신청
     * @return 쇼핑몰 정보
     */
    public ShopDto openRequest() {
        // 현재 사용자의 쇼핑몰 가져오기
        User currentUser = authFacade.extractUser();
        Shop targetShop = shopRepository.findShopByUserId(currentUser.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        // 쇼핑몰의 이름, 소개, 분류가 전부 작성된 상태라면
        if (targetShop.getName() != null && targetShop.getIntroduction() != null && targetShop.getCategory() != null) {
            targetShop.setStatus(ShopStatus.OPEN_REQUEST);
            return ShopDto.fromEntity(shopRepository.save(targetShop));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 관리자의 쇼핑몰 개설 신청 목록 확인
     * @return 개설 신청 목록
     */
    public List<ShopDto> openRequestList() {
        return shopRepository.getShopListWithOpenRequestStatus();
    }


    /**
     * 관리자의 쇼핑몰 개설 승인
     * @param shopId 쇼핑몰 ID
     * @return 쇼핑몰 정보
     */
    public ShopDto openRequestApproval(Long shopId) {
        Shop targetShop = shopRepository.findShopByUserId(shopId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        targetShop.setStatus(ShopStatus.OPEN);
        return ShopDto.fromEntity(shopRepository.save(targetShop));
    }

    /**
     * 관리자의 쇼핑몰 개설 거절 -> 사유 제출(추후 email 전송으로 구현)
     * @param shopId 쇼핑몰 ID
     * @return 쇼핑몰 정보
     */
    public ShopDto openRequestReject(Long shopId) {
        Shop targetShop = shopRepository.findShopByUserId(shopId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        targetShop.setStatus(ShopStatus.OPEN_NOT_ALLOWED);
        return ShopDto.fromEntity(shopRepository.save(targetShop));
    }

    /**
     * 쇼핑몰 주인의 쇼핑몰 폐쇄 요청
     * @return 쇼핑몰 정보
     */
    public ShopDto closeRequest() {
        // 현재 사용자의 쇼핑몰 가져오기
        User currentUser = authFacade.extractUser();
        Shop targetShop = shopRepository.findShopByUserId(currentUser.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        targetShop.setStatus(ShopStatus.CLOSE_REQUEST);
        return ShopDto.fromEntity(shopRepository.save(targetShop));
    }

    /**
     * 관리자의 쇼핑몰 폐쇄 요청 목록 확인
     * @return 폐쇄 요청 목록
     */
    public List<ShopDto> closeRequestList() {
        return shopRepository.getShopListWithCloseRequestStatus();
    }

    /**
     * 관리자의 쇼핑몰 폐쇄 요청 승인
     * @param shopId 쇼핑몰 ID
     * @return 폐쇄된 쇼핑몰 정보
     */
    public ShopDto closeRequestApproval(Long shopId) {
        Shop targetShop = shopRepository.findShopByUserId(shopId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        targetShop.setStatus(ShopStatus.CLOSED);
        return ShopDto.fromEntity(shopRepository.save(targetShop));
    }
}
