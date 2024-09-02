package com.example.market.domain.shop.service;

import com.example.market.domain.auth.entity.User;
import com.example.market.global.error.exception.ErrorCode;
import com.example.market.global.error.exception.GlobalCustomException;
import com.example.market.domain.auth.service.AuthenticationFacade;
import com.example.market.domain.shop.dto.ShopDto;
import com.example.market.domain.shop.dto.UpdateShopDto;
import com.example.market.domain.shop.constant.ShopStatus;
import com.example.market.domain.shop.dto.SearchShopDto;
import com.example.market.domain.shop.entity.Shop;
import com.example.market.domain.shop.repository.ShopRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new GlobalCustomException(ErrorCode.SHOP_NOT_EXISTS));
        // 쇼핑몰 수정
        targetShop.setName(dto.getName());
        targetShop.setIntroduction(dto.getIntroduction());
        targetShop.setShopCategory(dto.getShopCategory());
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
                .orElseThrow(() -> new GlobalCustomException(ErrorCode.SHOP_NOT_EXISTS));
        // 쇼핑몰의 이름, 소개, 분류가 전부 작성된 상태라면
        if (targetShop.getName() != null && targetShop.getIntroduction() != null && targetShop.getShopCategory() != null) {
            targetShop.setStatus(ShopStatus.OPEN_REQUEST);
            return ShopDto.fromEntity(shopRepository.save(targetShop));
        } else {
            throw new GlobalCustomException(ErrorCode.SHOP_INCOMPLETE);
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
        Shop targetShop = shopRepository.findById(shopId)
                .orElseThrow(() -> new GlobalCustomException(ErrorCode.SHOP_NOT_EXISTS));
        targetShop.setStatus(ShopStatus.OPEN);
        return ShopDto.fromEntity(shopRepository.save(targetShop));
    }

    /**
     * 관리자의 쇼핑몰 개설 거절 -> 사유 제출(추후 email 전송으로 구현)
     * @param shopId 쇼핑몰 ID
     * @return 쇼핑몰 정보
     */
    public ShopDto openRequestReject(Long shopId) {
        Shop targetShop = shopRepository.findById(shopId)
                .orElseThrow(() -> new GlobalCustomException(ErrorCode.SHOP_NOT_EXISTS));
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
                .orElseThrow(() -> new GlobalCustomException(ErrorCode.SHOP_NOT_EXISTS));
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
        Shop targetShop = shopRepository.findById(shopId)
                .orElseThrow(() -> new GlobalCustomException(ErrorCode.SHOP_NOT_EXISTS));
        targetShop.setStatus(ShopStatus.CLOSED);
        return ShopDto.fromEntity(shopRepository.save(targetShop));
    }

    /**
     * 쇼핑몰 조회
     * @param dto 쇼핑몰 검색 dto
     */
    public Page<ShopDto> getShopList(SearchShopDto dto, Pageable pageable) {
        return shopRepository.getShopListWithPages(dto, pageable);
    }
}
