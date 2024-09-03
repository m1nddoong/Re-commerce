package com.example.market.domain.shop.service;

import com.example.market.domain.shop.constant.DiscountRate;
import com.example.market.domain.shop.dto.CategoryDto;
import com.example.market.domain.shop.dto.DiscountDto;
import com.example.market.domain.shop.dto.SubCategoryDto;
import com.example.market.domain.shop.entity.SubCategory;
import com.example.market.domain.auth.entity.User;
import com.example.market.global.error.exception.GlobalCustomException;
import com.example.market.global.error.exception.ErrorCode;
import com.example.market.global.common.AuthenticationFacade;
import com.example.market.domain.shop.dto.SearchItemDto;
import com.example.market.domain.shop.repository.ItemRepository;
import com.example.market.domain.shop.dto.CreateItemDto;
import com.example.market.domain.shop.dto.ItemDto;
import com.example.market.domain.shop.entity.Item;
import com.example.market.domain.shop.entity.Category;
import com.example.market.domain.shop.repository.CategoryRepository;
import com.example.market.domain.shop.repository.SubCategoryRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final AuthenticationFacade authFacade;

    /**
     * 쇼핑몰 상품 등록
     *
     * @param dto 상품 이름, 상품 이미지, 상품 설명, 상품 가격, 상품 분류, 상품 소분류, 상품 재고
     * @return 상품
     */
    public ItemDto createItem(CreateItemDto dto) {
        User user = authFacade.extractUser();
        // 카테고리 지정
        Category targetCategory = getOrCreateCategory(dto.getItemCategory());
        // 서브 카테고리 지정
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

    /**
     * 쇼핑몰 상품 업데이트
     *
     * @param dto        상품 이름, 상품 이미지, 상품 설명, 상품 가격, 상품 분류, 상품 소분류, 상품 재고
     * @param shopItemId 쇼핑몰 상품 ID
     * @return 상품
     */
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

    /**
     * 쇼핑몰 상품 삭제
     *
     * @param shopItemId 쇼핑몰 ID
     */
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

    /**
     * 쇼핑몰 상품 검섹
     *
     * @param dto      상품 이름, 상품 분류, 소분류, 최대 가격, 최소가격
     * @param pageable 페이지 번호, 변위
     * @return 상품
     */
    public Page<ItemDto> getItems(SearchItemDto dto, Pageable pageable) {
        return itemRepository.getItemListWithPages(dto, pageable);
    }


    /**
     * 카테고리 찾기, 없을 경우 생성
     */
    private Category getOrCreateCategory(String categoryName) {
        return categoryRepository.findByName(categoryName)
                .orElseGet(() -> {
                    Category newCategory = Category.builder()
                            .name(categoryName)
                            .build();
                    return categoryRepository.save(newCategory);
                });
    }
    /**
     * 서브 카테고리 조회 및 생성
     */
    private SubCategory getOrCreateSubCategory(String subCategoryName, Category parentCategory) {
        return subCategoryRepository.findByNameAndCategory(subCategoryName, parentCategory)
                .orElseGet(() -> {
                    SubCategory newSubCategory = SubCategory.builder()
                            .name(subCategoryName)
                            .category(parentCategory)
                            .build();
                    return subCategoryRepository.save(newSubCategory);
                });
    }



    /**
     * 전체 카테고리 조회
     *
     * @return 전체 카테고리 리스트
     */
    public List<CategoryDto> getCategoryList() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 특정 카테고리의 서브 카테고리 조회
     *
     * @param categoryId 카테고리 ID
     * @return 서브 카테고리
     */
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

    /**
     * 카테고리 통합
     *
     * @param categoryId1 통합될 카테고리
     * @param categoryId2 통합할 카테고리
     */
    public void mergeCategories(Long categoryId1, Long categoryId2) {
        Category category1 = categoryRepository.findById(categoryId1)
                .orElseThrow(() -> new GlobalCustomException(ErrorCode.ITEM_CATEGORY_NOT_FOUND));
        Category category2 = categoryRepository.findById(categoryId2)
                .orElseThrow(() -> new GlobalCustomException(ErrorCode.ITEM_CATEGORY_NOT_FOUND));

        // 카테고리 2 를 가진 상품의 카테고리를 카테고리1 로 변경
        List<Item> items = itemRepository.findByCategoryId(categoryId2);
        for (Item item : items) {
            item.setCategory(category1);
        }
        itemRepository.saveAll(items);

        // category2를 참조하는 모든 서브카테고리들을 category1로 업데이트
        List<SubCategory> subCategories = subCategoryRepository.findByCategoryId(categoryId2);
        List<SubCategory> category1SubCategories = subCategoryRepository.findByCategoryId(categoryId1);
        for (SubCategory subCategory : subCategories) {
            // category1에 같은 이름을 가진 서브카테고리가 있는지 찾기
            Optional<SubCategory> existingSubCategoryOpt = category1SubCategories.stream()
                    .filter(c1SubCat -> c1SubCat.getName().equals(subCategory.getName()))
                    .findFirst();

            if (existingSubCategoryOpt.isPresent()) {
                SubCategory existingSubCategory = existingSubCategoryOpt.get();
                List<Item> subCategoryItems = itemRepository.findBySubCategoryId(subCategory.getId());
                for (Item item : subCategoryItems) {
                    item.setSubCategory(existingSubCategory);
                }
                itemRepository.saveAll(subCategoryItems);
                subCategoryRepository.delete(subCategory);
            } else {
                // 같은 이름의 서브 카테고리가 없을 경우, 카테고리1로 이동
                subCategory.setCategory(category1);
                subCategoryRepository.save(subCategory);
            }
        }
        // 병합할 카테고리 삭제
        categoryRepository.delete(category2);

    }

    /**
     * 서브 카테고리 통합
     *
     * @param subCategoryId1 통합될 서브 카테고리
     * @param subCategoryId2 통합할 서브 카테고리
     */
    public void mergeSubCategories(Long subCategoryId1, Long subCategoryId2) {
        // 일단 같은 카테고리여야함
        SubCategory subCategory1 = subCategoryRepository.findById(subCategoryId1).
                orElseThrow(() -> new GlobalCustomException(ErrorCode.ITEM_SUBCATEGORY_NOT_FOUND));
        SubCategory subCategory2 = subCategoryRepository.findById(subCategoryId2).
                orElseThrow(() -> new GlobalCustomException(ErrorCode.ITEM_SUBCATEGORY_NOT_FOUND));
        if (!subCategory1.getCategory().equals(subCategory2.getCategory())) {
            throw new GlobalCustomException(ErrorCode.ITEM_CATEGORY_NOT_EQUAL);
        }
        List<Item> items = itemRepository.findBySubCategoryId(subCategoryId2);
        for (Item item : items) {
            item.setSubCategory(subCategory1);
        }
        itemRepository.saveAll(items);
        subCategoryRepository.delete(subCategory2);
    }

    /**
     * 상품 할인
     *
     * @param dto itemId, expiredDate, discountRate
     */
    public ItemDto itemSale(DiscountDto dto) {
        User currentUser = authFacade.extractUser();
        Item item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new GlobalCustomException(ErrorCode.ITEM_NOT_EXISTS));
        // 사용자 확인
        if (!currentUser.getUsername().equals(item.getShop().getUser().getUsername())) {
            throw new GlobalCustomException(ErrorCode.ITEM_NO_PERMISSION);
        }
        DiscountRate discountRate = DiscountRate.of(dto.getDiscountRete());
        BigDecimal discountedPrice = discountRate.applyDiscount(item.getPrice());
        item.setDiscountedPrice(discountedPrice);
        item.setDiscountExpirationDate(dto.getExpirationDate());
        return ItemDto.fromEntity(itemRepository.save(item));
    }

    /**
     * 만료일이 지난 할인 적용 상품 가격 복원
     */
    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정마다 실행
    public void restoreExpiredDiscounts() {
        List<Item> items = itemRepository.findAll();

        for (Item item : items) {
            if (item.getDiscountExpirationDate() != null && item.getDiscountExpirationDate()
                    .isBefore(LocalDateTime.now())) {
                item.setDiscountedPrice(null); // 할인된 가격 제거
                item.setDiscountExpirationDate(null); // 만료일 제거
                itemRepository.save(item);
            }
        }
    }


}


