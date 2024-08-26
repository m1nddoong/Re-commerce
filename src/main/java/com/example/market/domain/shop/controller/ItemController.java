package com.example.market.domain.shop.controller;


import com.example.market.domain.shop.dto.DiscountDto;
import com.example.market.domain.shop.dto.SearchItemDto;
import com.example.market.domain.shop.dto.CreateItemDto;
import com.example.market.domain.shop.dto.ItemDto;
import com.example.market.domain.shop.dto.CategoryDto;
import com.example.market.domain.shop.dto.SubCategoryDto;
import com.example.market.domain.shop.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "item", description = "쇼핑몰 상품에 관한 API")
@RestController
@RequestMapping("/api/v1/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    // 상품 등록
    @PostMapping("/create")
    @Operation(
            summary = "쇼핑몰 상품 등록",
            description = "<p>쇼핑몰 주인은 쇼핑몰에 상품을 등록할 수 있다.</p>"
                    + "<p>필수적인 정보는 상품 이름, 상품 이미지, 상품 설명, 상품 가격, 상품 분류, 상품 소분류, 상품 재고가 있다.</p>"
                    + "<p>상품 분류와 소분류는 쇼핑몰의 주인이 추가하거나, 다른 쇼핑몰 주인이 추가했던 분류를 바탕으로 선택할 수 있다.</p>"
    )
    private ResponseEntity<ItemDto> createItem(
            @RequestBody
            CreateItemDto dto
    ) {
        return ResponseEntity.ok(itemService.createItem(dto));
    }

    // 상품 수정
    @PutMapping("/update/{shopItemId}")
    @Operation(
            summary = "쇼핑몰 상품 수정",
            description = "<p>쇼핑몰 주인은 등록된 상품을 수정할 수 있다.</p>"
    )
    private ResponseEntity<ItemDto> updateItem(
            @RequestBody
            CreateItemDto dto,
            @PathVariable(value = "shopItemId")
            Long shopItemId
    ) {
        return ResponseEntity.ok(itemService.updateItem(dto, shopItemId));
    }

    // 상품 삭제
    @DeleteMapping("/delete/{shopItemId}")
    @Operation(
            summary = "쇼핑몰 상품 삭제",
            description = "<p>쇼핑몰 주인은 등록된 상품을 삭제할 수 있다.</p>"
    )
    private ResponseEntity<Void> deleteItem(
            @PathVariable(value = "shopItemId")
            Long shopItemId
    ) {
        itemService.deleteItem(shopItemId);
        return ResponseEntity.ok().build();
    }

    // 쇼핑몰 상품 검색
    @PostMapping("/search")
    @Operation(
            summary = "쇼핑몰 상품 검색",
            description = "<p>비활성 사용자를 제외한 사용자는 쇼핑몰의 상품을 검색할 수 있다.</p>"
                    + "<p>이름, 상품 분류, 상품 소분류, 가격 범위를 기준으로 상품을 검색할 수 있다.</p>"
                    + "<p>단, 분류와 소분류는 하나만 선택이 가능하다.</p>"
                    + "<p>조회되는 상품이 등록된 쇼핑몰에 대한 정보가 함꼐 제공되어야 한다.</p>"
    )
    private ResponseEntity<Page<ItemDto>> getItems(
            @RequestBody
            SearchItemDto dto,
            Pageable pageable
    ) {
        return ResponseEntity.ok(itemService.getItems(dto, pageable));
    }

    // 쇼핑몰 상품 카테고리 전체 조회
    @GetMapping("/categories")
    @Operation(
            summary = "상품 분류 목록 조회",
            description = "<p>사용자는 상품 분류 목록을 보고 상품 등록 시 카테고리를 선택할 수 있다.</p>"
    )
    public ResponseEntity<List<CategoryDto>> getCategoryList() {
        return ResponseEntity.ok(itemService.getCategoryList());
    }


    // 특정 카테고리에 따른 서브 카테고리 조회
    @GetMapping("/categories/{categoryId}")
    @Operation(
            summary = "상품 소분류 목록 조회",
            description = "<p>사용자는 상품 소분류 목록을 보고 상품 등록 시 서브 카테고리를 선택할 수 있다.</p>"

    )
    public ResponseEntity<SubCategoryDto> getSubCategoryList(
            @PathVariable("categoryId")
            Long categoryId
    ) {
        return ResponseEntity.ok(itemService.getSubCategoryList(categoryId));
    }

    @PutMapping("/categories/merge/{categoryId1}/{categoryId2}")
    @Operation(
            summary = "상품 분류 통합",
            description = "<p>관리자는 상품 분류 목록을 보고, 유사한 분류를 같은 분류가 될 수 있도록 분류를 수정할 수 있다.</p>"
                    + "<p>'categoryId2' PK를 가진 카테고리가 'categoryId1' PK를 가진 카테고리로 통합된다 (ex. \"피부\", \"스킨\" -> \"스킨\" 통합)</p>"
                    + "<p>이떄, 각 카테고리에 있던 서브 카테고리들 간 이름이 같은 것들은 서로 통합되고. 그렇지 않다면 서브 카테고리가 추가된다.</p>"
    )
    public ResponseEntity<Void> mergeCategories(
            @PathVariable("categoryId1") // 통합될 카테고리
            Long categoryId1,
            @PathVariable("categoryId2") // 통합할 카테고리
            Long categoryId2
    ) {
        itemService.mergeCategories(categoryId1, categoryId2);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/categories/merge/sub/{subCategoryId1}/{subCategoryId2}")
    @Operation(
            summary = "상품 소분류 통합",
            description = "<p>관리자는 상품 소분류 목록을 보고, 유사한 소분류를 같은 소분류가 될 수 있도록 소분류를 수정할 수 있다.</p>"
                    + "<p>'subCategoryId2' PK를 가진 서브 카테고리가 'subCategoryId1' PK를 가진 서브 카테고리로 통합된다</p>"
    )
    public ResponseEntity<Void> mergeSubCategories(
            @PathVariable("subCategoryId1")
            Long subCategoryId1,
            @PathVariable("subCategoryId2")
            Long subCategoryId2
    ) {
        itemService.mergeSubCategories(subCategoryId1, subCategoryId2);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/sale")
    @Operation(
            summary = "상품 할인 적용",
            description = "<p>쇼핑몰 주인은 등록한 상품의 할인을 진행할 수 있다.</p>"
                    + "<p>기한과 할인율을 바탕으로, 할인 가격이 자동으로 적용되도록 한다.</p>"
    )
    public ResponseEntity<ItemDto> itemSale(
        @RequestBody
        DiscountDto dto
    ) {
        return ResponseEntity.ok(itemService.itemSale(dto));
    }

}
