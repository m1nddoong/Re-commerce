package com.example.market.shop.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShop is a Querydsl query type for Shop
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShop extends EntityPathBase<Shop> {

    private static final long serialVersionUID = -624717366L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShop shop = new QShop("shop");

    public final com.example.market.common.QBaseEntity _super = new com.example.market.common.QBaseEntity(this);

    public final StringPath address = createString("address");

    public final StringPath coordinates = createString("coordinates");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath introduction = createString("introduction");

    //inherited
    public final BooleanPath isDelete = _super.isDelete;

    public final ListPath<Item, QItem> items = this.<Item, QItem>createList("items", Item.class, QItem.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> lastTransactionDate = createDateTime("lastTransactionDate", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final EnumPath<com.example.market.shop.constant.ShopCategory> shopCategory = createEnum("shopCategory", com.example.market.shop.constant.ShopCategory.class);

    public final EnumPath<com.example.market.shop.constant.ShopStatus> status = createEnum("status", com.example.market.shop.constant.ShopStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.example.market.auth.entity.QUser user;

    public QShop(String variable) {
        this(Shop.class, forVariable(variable), INITS);
    }

    public QShop(Path<? extends Shop> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShop(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShop(PathMetadata metadata, PathInits inits) {
        this(Shop.class, metadata, inits);
    }

    public QShop(Class<? extends Shop> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.example.market.auth.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

