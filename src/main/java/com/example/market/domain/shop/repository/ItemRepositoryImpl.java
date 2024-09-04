package com.example.market.domain.shop.repository;

import com.example.market.domain.shop.dto.SearchItemDto;
import com.example.market.domain.shop.dto.ItemDto;
import com.example.market.domain.shop.entity.QItem;
import com.example.market.domain.shop.entity.QShop;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
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
public class ItemRepositoryImpl implements ItemRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QItem item = QItem.item;
    private final QShop shop = QShop.shop;

    @Override
    public Page<ItemDto> getItemListWithPages(SearchItemDto dto, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        // 필터 조건 추가
        builder.and(nameContains(dto.getName()));
        builder.and(priceBetween(dto.getMinPrice(), dto.getMaxPrice()));
        builder.and(categoryContains(dto.getItemCategory(), dto.getItemSubCategory()));
        // 쇼핑몰과 상품을 조인
        List<ItemDto> results = jpaQueryFactory
                .select(Projections.constructor(ItemDto.class,
                        item.name,
                        item.img,
                        item.description,
                        item.price,
                        item.discountedPrice,
                        item.discountExpirationDate,
                        item.category.name,
                        item.subCategory.name,
                        item.stock,
                        shop.id.as("shopId")
                ))
                .from(item)
                .leftJoin(item.shop, shop)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(item.createdAt.desc())
                .fetch();

        // 전체 상품 수 계산
        long total = Optional.ofNullable(jpaQueryFactory
                .select(item.count())
                .from(item)
                .leftJoin(item.shop, shop)
                .where(builder)
                .fetchOne())
                .orElse(0L);

        return new PageImpl<>(results, pageable, total);
    }

    // 이름 조건
    private BooleanExpression nameContains(String name) {
        return name == null || name.isEmpty() ? null : item.name.containsIgnoreCase(name);
    }

    // 상품 카테고리 조건
    private BooleanExpression categoryContains(String itemCategoryName, String itemSubCategoryName) {
        // 카테고리 이름이 제공된 경우, 카테고리 조건 추가
        if (itemCategoryName != null && !itemCategoryName.isEmpty()) {
            return item.category.name.eq(itemCategoryName);
        }

        // 서브 카테고리 이름이 제공된 경우, 서브 카테고리 조건 추가
        else {
            return item.subCategory.name.eq(itemSubCategoryName);
        }
        // 둘다 제공된 경우는 없음
    }

    // 가격 범위 조건
    private BooleanExpression priceBetween(Integer minPrice, Integer maxPrice) {
        if (minPrice == null && maxPrice == null) return null;
        if (minPrice == null) return priceLoe(maxPrice);
        if (maxPrice == null) return priceGoe(minPrice);
        return item.price.between(minPrice, maxPrice);
    }

    // 최대 가격 조건
    private BooleanExpression priceLoe(Integer value) {
        return value == null ? null : item.price.loe(value);
    }

    // 최소 가격 조건
    private BooleanExpression priceGoe(Integer value) {
        return value == null ? null : item.price.goe(value);
    }
}
