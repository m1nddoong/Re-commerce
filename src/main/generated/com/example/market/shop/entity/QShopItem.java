package com.example.market.shop.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShopItem is a Querydsl query type for ShopItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShopItem extends EntityPathBase<ShopItem> {

    private static final long serialVersionUID = -942371843L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShopItem shopItem = new QShopItem("shopItem");

    public final com.example.market.common.QBaseEntity _super = new com.example.market.common.QBaseEntity(this);

    public final EnumPath<com.example.market.shop.constant.ItemCategory> category = createEnum("category", com.example.market.shop.constant.ItemCategory.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath img = createString("img");

    //inherited
    public final BooleanPath isDelete = _super.isDelete;

    public final StringPath name = createString("name");

    public final StringPath price = createString("price");

    public final QShop shop;

    public final NumberPath<Integer> stock = createNumber("stock", Integer.class);

    public final EnumPath<com.example.market.shop.constant.ItemSubCategory> subCategory = createEnum("subCategory", com.example.market.shop.constant.ItemSubCategory.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QShopItem(String variable) {
        this(ShopItem.class, forVariable(variable), INITS);
    }

    public QShopItem(Path<? extends ShopItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShopItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShopItem(PathMetadata metadata, PathInits inits) {
        this(ShopItem.class, metadata, inits);
    }

    public QShopItem(Class<? extends ShopItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.shop = inits.isInitialized("shop") ? new QShop(forProperty("shop"), inits.get("shop")) : null;
    }

}

