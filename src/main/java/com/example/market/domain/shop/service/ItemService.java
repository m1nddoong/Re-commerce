package com.example.market.domain.shop.service;

import com.example.market.domain.shop.dto.CategoryDto;
import com.example.market.domain.shop.dto.SubCategoryDto;
import com.example.market.domain.shop.entity.SubCategory;
import com.example.market.domain.user.entity.User;
import com.example.market.global.error.exception.GlobalCustomException;
import com.example.market.global.error.exception.ErrorCode;
import com.example.market.global.auth.AuthenticationFacade;
import com.example.market.domain.shop.dto.SearchItemDto;
import com.example.market.domain.shop.repository.ItemRepository;
import com.example.market.domain.shop.dto.CreateItemDto;
import com.example.market.domain.shop.dto.ItemDto;
import com.example.market.domain.shop.entity.Item;
import com.example.market.domain.shop.entity.Category;
import com.example.market.domain.shop.repository.CategoryRepository;
import com.example.market.domain.shop.repository.SubCategoryRepository;
import java.util.List;
import java.util.stream.Collectors;
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
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final AuthenticationFacade authFacade;

    // 쇼핑몰 상품 등록
    public ItemDto createItem(CreateItemDto dto) {
        User user = authFacade.extractUser();
        Category targetCategory = getOrCreateCategory(dto.getItemCategory());
        SubCategory targetSubCategory = getOrCreateSubCategory(dto.getItemSubCategory(), targetCategory);
        return ItemDto.fromEntity(itemRepository.save(Item.builder()
                .name(dto.getName())
                .img(dto.getImg())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .category(targetCategory)
                .subCategory(targetSubCategory)
                .stock(dto.getStock())
                .shop(user.getShop())
                .build()));
    }

    // 쇼핑몰 상품 업데이트
    public ItemDto updateItem(CreateItemDto dto, Long shopItemId) {
        // 상품 조회
        Item targetItem = itemRepository.findById(shopItemId)
                .orElseThrow(() -> new GlobalCustomException(ErrorCode.ITEM_NOT_EXISTS));
        // 상품의 주인이 맞는지
        User user = authFacade.extractUser();
        if (!targetItem.getShop().getUser().getId().equals(user.getId())) {
            throw new GlobalCustomException(ErrorCode.ITEM_NO_PERMISSION);
        }
        // 카테고리와 서브 카테고리 조회
        Category targetCategory = getOrCreateCategory(dto.getItemCategory());
        SubCategory targetSubCategory = getOrCreateSubCategory(dto.getItemSubCategory(), targetCategory);

        targetItem.setName(dto.getName());
        targetItem.setImg(dto.getImg());
        targetItem.setDescription(dto.getDescription());
        targetItem.setPrice(dto.getPrice());
        targetItem.setCategory(targetCategory);
        targetItem.setSubCategory(targetSubCategory);
        targetItem.setStock(dto.getStock());
        return ItemDto.fromEntity(itemRepository.save(targetItem));
    }

    // 쇼핑몰 상품 삭제
    public void deleteItem(Long shopItemId) {
        // 상품 조회
        Item targetItem = itemRepository.findById(shopItemId)
                .orElseThrow(() -> new GlobalCustomException(ErrorCode.ITEM_NOT_EXISTS));
        // 상품의 주인이 맞는지
        User user = authFacade.extractUser();
        if (!targetItem.getShop().getUser().getId().equals(user.getId())) {
            throw new GlobalCustomException(ErrorCode.ITEM_NO_PERMISSION);
        }
        itemRepository.deleteById(shopItemId);
    }

    // 쇼핑몰 상품 검섹
    public Page<ItemDto> getItems(SearchItemDto dto, Pageable pageable) {
        return itemRepository.getItemListWithPages(dto, pageable);
    }


    // 카테고리 찾기, 없을 경우 생성
    private Category getOrCreateCategory(String categoryName) {
        return categoryRepository.findByName(categoryName)
                .orElseGet(() -> {
                    Category newCategory = Category.builder()
                            .name(categoryName)
                            .build();
                    return categoryRepository.save(newCategory);
                });
    }

    // 서브 카테고리 찾기, 없을 경우 생성
    private SubCategory getOrCreateSubCategory(String subCategoryName, Category parentCategory) {
        return subCategoryRepository.findByName(subCategoryName)
                .orElseGet(() -> {
                    SubCategory newSubCategory = SubCategory.builder()
                            .name(subCategoryName)
                            .category(parentCategory)
                            .build();
                    return subCategoryRepository.save(newSubCategory);
                });
    }


    // 전체 카테고리 조회
    public List<CategoryDto> getCategoryList() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 특정 카테고리의 서브 카테고리 조회
    public SubCategoryDto getSubCategoryList(Long categoryId) {
        // 특정 카테고리 찾기
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new GlobalCustomException(ErrorCode.ITEM_CATEGORY_NOT_FOUND));
        // 특정 카테고리 하위 서브 카테고리 리스트 조회
        List<SubCategory> subCategories = subCategoryRepository.findByCategoryId(categoryId);

        // DTO 생성
        List<String> subCategoryNames = subCategories.stream()
                .map(SubCategory::getName)
                .collect(Collectors.toList());

        return SubCategoryDto.builder()
                .categoryName(category.getName())
                .subCategoryName(subCategoryNames)
                .build();
    }

    // 인자가 없으면 전체 카테고리 조회

    // 인자가 있으면, 특정 카테고리와, 그 하위 서브 카테고리들 조

}
