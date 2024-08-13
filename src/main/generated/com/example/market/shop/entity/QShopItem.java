package com.example.market.shop.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QShopItem is a Querydsl query type for ShopItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShopItem extends EntityPathBase<ShopItem> {

    private static final long serialVersionUID = -942371843L;

    public static final QShopItem shopItem = new QShopItem("shopItem");

    public final com.example.market.common.QBaseEntity _super = new com.example.market.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath isDelete = _super.isDelete;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QShopItem(String variable) {
        super(ShopItem.class, forVariable(variable));
    }

    public QShopItem(Path<? extends ShopItem> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShopItem(PathMetadata metadata) {
        super(ShopItem.class, metadata);
    }

}

