package com.example.market.shop.repo;

import com.example.market.shop.dto.ItemDto;
import com.example.market.shop.dto.SearchItemDto;
import com.example.market.shop.entity.QItem;
import com.example.market.shop.entity.QShop;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    QItem item = QItem.item;
    QShop shop = QShop.shop;

    @Override
    public Page<ItemDto> getItemListWithPages(SearchItemDto dto, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        // 이름으로 필터링
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            builder.and(item.name.containsIgnoreCase(dto.getName()));
        }

        // 가격 범위로 필터링
        if (dto.getMinPrice() != null) {
            builder.and(item.price.goe(dto.getMinPrice()));
        }
        if (dto.getMaxPrice() != null) {
            builder.and(item.price.loe(dto.getMaxPrice()));
        }

        // 쇼핑몰과 상품을 조인
        List<ItemDto> results = jpaQueryFactory
                .select(Projections.constructor(ItemDto.class,
                        item.id,
                        item.name,
                        item.price,
                        item.stock,
                        shop.id.as("shopId"),
                        shop.name.as("shopName")
                ))
                .from(item)
                .leftJoin(item.shop, shop)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(item.createdAt.desc())
                .fetch();

        // 전체 상품 수 계산
        long total = jpaQueryFactory
                .select(item.count())
                .from(item)
                .leftJoin(item.shop, shop)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }
}
