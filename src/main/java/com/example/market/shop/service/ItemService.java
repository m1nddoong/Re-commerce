package com.example.market.shop.service;

import com.example.market.auth.entity.User;
import com.example.market.common.exception.GlobalCustomException;
import com.example.market.common.exception.GlobalErrorCode;
import com.example.market.common.util.AuthenticationFacade;
import com.example.market.shop.constant.ItemCategory;
import com.example.market.shop.constant.ItemSubCategory;
import com.example.market.shop.dto.CreateItemDto;
import com.example.market.shop.dto.ItemDto;
import com.example.market.shop.dto.SearchItemDto;
import com.example.market.shop.entity.Item;
import com.example.market.shop.repo.ItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final AuthenticationFacade authFacade;

    // 쇼핑몰 상품 등록
    public ItemDto createItem(CreateItemDto dto) {
        User user = authFacade.extractUser();
        return ItemDto.fromEntity(itemRepository.save(Item.builder()
                .name(dto.getName())
                .img(dto.getImg())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .itemCategory(ItemCategory.valueOf(dto.getItemCategory()))
                .itemSubCategory(ItemSubCategory.valueOf(dto.getItemSubCategory()))
                .stock(dto.getStock())
                .shop(user.getShop())
                .build()));
    }

    // 쇼핑몰 상품 업데이트
    public ItemDto updateItem(CreateItemDto dto, Long shopItemId) {
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
        targetItem.setItemCategory(ItemCategory.valueOf(dto.getItemCategory()));
        targetItem.setItemSubCategory(ItemSubCategory.valueOf(dto.getItemSubCategory()));
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

    // 쇼핑몰 상품 검섹
    public Page<ItemDto> getItems(SearchItemDto dto, Pageable pageable) {
        return itemRepository.getItemListWithPages(dto, pageable);
    }
}
