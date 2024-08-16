package com.example.market.shop.service;

import com.example.market.auth.entity.User;
import com.example.market.common.exception.GlobalCustomException;
import com.example.market.common.exception.GlobalErrorCode;
import com.example.market.common.util.AuthenticationFacade;
import com.example.market.shop.constant.ItemCategory;
import com.example.market.shop.constant.ItemSubCategory;
import com.example.market.shop.dto.ItemDto;
import com.example.market.shop.entity.Item;
import com.example.market.shop.repo.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final AuthenticationFacade authFacade;

    // 쇼핑몰 상품 등록
    public ItemDto createItem(ItemDto dto) {
        User user = authFacade.extractUser();
        return ItemDto.fromEntity(itemRepository.save(Item.builder()
                .name(dto.getName())
                .img(dto.getImg())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .category(ItemCategory.valueOf(dto.getCategory()))
                .subCategory(ItemSubCategory.valueOf(dto.getSubCategory()))
                .stock(dto.getStock())
                .shop(user.getShop())
                .build()));
    }

    // 쇼핑몰 상품 업데이트
    public ItemDto updateItem(ItemDto dto, Long shopItemId) {
        // 상품 조회
        Item targetItem = itemRepository.findById(shopItemId)
                .orElseThrow(() -> new GlobalCustomException(GlobalErrorCode.ITEM_NOT_EXISTS));
        // 상품의 주인이 맞는지
        User user = authFacade.extractUser();
        if (!targetItem.getShop().getUser().getId().equals(user.getId())) {
            throw new GlobalCustomException(GlobalErrorCode.ITEM_NO_PERMISSION);
        }

        targetItem.setName(dto.getName());
        targetItem.setImg(dto.getImg());
        targetItem.setDescription(dto.getDescription());
        targetItem.setPrice(dto.getPrice());
        targetItem.setCategory(ItemCategory.valueOf(dto.getCategory()));
        targetItem.setSubCategory(ItemSubCategory.valueOf(dto.getSubCategory()));
        targetItem.setStock(dto.getStock());
        return ItemDto.fromEntity(itemRepository.save(targetItem));
    }

    // 쇼핑몰 상품 삭제
    public void deleteItem(Long shopItemId) {
        // 상품 조회
        Item targetItem = itemRepository.findById(shopItemId)
                .orElseThrow(() -> new GlobalCustomException(GlobalErrorCode.ITEM_NOT_EXISTS));
        // 상품의 주인이 맞는지
        User user = authFacade.extractUser();
        if (!targetItem.getShop().getUser().getId().equals(user.getId())) {
            throw new GlobalCustomException(GlobalErrorCode.ITEM_NO_PERMISSION);
        }
        itemRepository.deleteById(shopItemId);
    }

}
