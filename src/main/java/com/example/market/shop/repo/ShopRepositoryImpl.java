package com.example.market.shop.repo;

import com.example.market.shop.constant.ItemCategory;
import com.example.market.shop.constant.ItemSubCategory;
import com.example.market.shop.constant.ShopCategory;
import com.example.market.shop.constant.ShopStatus;
import com.example.market.shop.dto.ItemDto;
import com.example.market.shop.dto.SearchShopDto;
import com.example.market.shop.dto.ShopDto;
import com.example.market.shop.entity.QItem;
import com.example.market.shop.entity.QShop;
import com.example.market.shop.entity.Shop;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
@Repository
@RequiredArgsConstructor
public class ShopRepositoryImpl implements ShopRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<ShopDto> getShopList(SearchShopDto dto) {
        QShop shop = QShop.shop;
        QItem item = QItem.item;

        BooleanBuilder builder = new BooleanBuilder();

        // 이름으로 필터링
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            builder.and(shop.name.containsIgnoreCase(dto.getName()));
        }

        // 쇼핑몰 분류로 필터링
        if (dto.getShopCategory() != null && !dto.getShopCategory().isEmpty()) {
            builder.and(shop.shopCategory.eq(ShopCategory.valueOf(dto.getShopCategory())));
        }

        // 상품 분류로 필터링
        if (dto.getItemCategory() != null && !dto.getItemCategory().isEmpty()) {
            builder.and(item.itemCategory.eq(ItemCategory.valueOf(dto.getItemCategory())));
        }

        // 상품 소분류로 필터링
        if (dto.getItemSubCategory() != null && !dto.getItemSubCategory().isEmpty()) {
            builder.and(item.itemSubCategory.eq(ItemSubCategory.valueOf(dto.getItemSubCategory())));
        }

        // 쿼리 생성
        List<ShopDto> shops = jpaQueryFactory
                .select(Projections.constructor(ShopDto.class,
                        shop.id,
                        shop.name,
                        shop.shopCategory,
                        Expressions.list(
                                Projections.constructor(ItemDto.class,
                                        item.id,
                                        item.name,
                                        item.itemCategory,
                                        item.itemSubCategory
                                )
                        )
                ))
                .from(shop)
                .leftJoin(shop.items, item)
                .where(builder)
                .groupBy(shop.id)
                .orderBy(shop.lastTransactionDate.desc()) // 가장 최근 거래일 순서로 정렬
                .fetch();

        return shops;
    }



    @Override
    public List<ShopDto> getShopListWithOpenRequestStatus() {
        QShop shop = QShop.shop;
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
        QShop shop = QShop.shop;
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
