package com.example.market.shop.repo;

import com.example.market.shop.constant.ShopStatus;
import com.example.market.shop.dto.ShopDto;
import com.example.market.shop.entity.QShop;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
@Repository
@RequiredArgsConstructor
public class ShopRepositoryImpl implements ShopRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<ShopDto> getShopListWithOpenRequestStatus() {
        QShop shop = QShop.shop;
        return jpaQueryFactory
                .select(Projections.constructor(ShopDto.class,
                        shop.name,
                        shop.introduction,
                        shop.category,
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
                        shop.name,
                        shop.introduction,
                        shop.category,
                        shop.status,
                        shop.user.username,
                        shop.address,
                        shop.coordinates,
                        shop.items))
                .from(shop)
                .where(shop.status.eq(ShopStatus.CLOSE_REQUEST))
                .fetch();
    }
}
