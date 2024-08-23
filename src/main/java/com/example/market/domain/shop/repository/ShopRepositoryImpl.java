package com.example.market.domain.shop.repository;

import com.example.market.domain.shop.entity.QItem;
import com.example.market.domain.shop.entity.QShop;
import com.example.market.global.error.exception.GlobalCustomException;
import com.example.market.global.error.exception.ErrorCode;
import com.example.market.domain.shop.dto.ShopDto;
import com.example.market.domain.shop.constant.ShopCategory;
import com.example.market.domain.shop.constant.ShopStatus;
import com.example.market.domain.shop.dto.SearchShopDto;
import com.example.market.domain.shop.entity.Category;
import com.example.market.domain.shop.entity.SubCategory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ShopRepositoryImpl implements ShopRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final QShop shop = QShop.shop;
    private final QItem item = QItem.item;


    @Override
    public Page<ShopDto> getShopListWithPages(SearchShopDto dto, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        // 이름으로 필터링
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            builder.and(shop.name.containsIgnoreCase(dto.getName()));
        }

        // 쇼핑몰 분류로 필터링
        if (dto.getShopCategory() != null && !dto.getShopCategory().isEmpty()) {
            builder.and(shop.shopCategory.eq(ShopCategory.valueOf(dto.getShopCategory())));
        }

        // 상품 카테고리로 필터링
        if (dto.getItemCategory() != null && !dto.getItemCategory().isEmpty()) {
            Category category = categoryRepository.findByName(dto.getItemCategory())
                    .orElseThrow(() -> new GlobalCustomException(ErrorCode.ITEM_CATEGORY_NOT_FOUND));
            builder.and(item.category.eq(category));
        }

        // 상품 서브 카테고리로 필터링
        if (dto.getItemSubCategory() != null && !dto.getItemSubCategory().isEmpty()) {
            SubCategory subCategory = subCategoryRepository.findByName(dto.getItemSubCategory())
                    .orElseThrow(() -> new GlobalCustomException(ErrorCode.ITEM_SUBCATEGORY_NOT_FOUND));
            builder.and(item.subCategory.eq(subCategory));
        }

        // 쿼리 생성
        List<ShopDto> results = jpaQueryFactory
                .select(Projections.constructor(ShopDto.class,
                        shop.id,
                        shop.name,
                        shop.introduction,
                        shop.shopCategory,
                        shop.status,
                        shop.user.username,
                        shop.address,
                        shop.coordinates
                ))
                .from(shop)
                .leftJoin(shop.items, item)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(shop.lastTransactionDate.desc()) // 가장 최근 거래일 순서로 정렬
                // 결과를 그룹화하여 중복 제거 - 집계함수와 사용되어 각 그룹의 단일 레코드 생성
                .groupBy(shop.id, shop.name, shop.introduction, shop.shopCategory, shop.status, shop.user.username,
                        shop.address, shop.coordinates)
                .fetch();

        long total = Optional.ofNullable(jpaQueryFactory
                .select(shop.count())
                .from(shop)
                .leftJoin(shop.items, item)
                .where(builder)
                .fetchOne())
                .orElse(0L);

        return new PageImpl<>(results, pageable, total);
    }


    @Override
    public List<ShopDto> getShopListWithOpenRequestStatus() {
        return jpaQueryFactory
                .select(Projections.constructor(ShopDto.class,
                        shop.id,
                        shop.name,
                        shop.introduction,
                        shop.shopCategory,
                        shop.status,
                        shop.user.username,
                        shop.address,
                        shop.coordinates))
                .from(shop)
                .where(shop.status.eq(ShopStatus.OPEN_REQUEST))
                .fetch();
    }


    @Override
    public List<ShopDto> getShopListWithCloseRequestStatus() {
        return jpaQueryFactory
                .select(Projections.constructor(ShopDto.class,
                        shop.id,
                        shop.name,
                        shop.introduction,
                        shop.shopCategory,
                        shop.status,
                        shop.user.username,
                        shop.address,
                        shop.coordinates))
                .from(shop)
                .where(shop.status.eq(ShopStatus.CLOSE_REQUEST))
                .fetch();
    }
}
