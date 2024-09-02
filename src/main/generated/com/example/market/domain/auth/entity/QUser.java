package com.example.market.domain.auth.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 254831129L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final com.example.market.global.common.QBaseEntity _super = new com.example.market.global.common.QBaseEntity(this);

    public final DatePath<java.time.LocalDate> birthday = createDate("birthday", java.time.LocalDate.class);

    public final StringPath businessNum = createString("businessNum");

    public final EnumPath<com.example.market.domain.auth.constant.BusinessStatus> businessStatus = createEnum("businessStatus", com.example.market.domain.auth.constant.BusinessStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath isDelete = _super.isDelete;

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final StringPath profileImg = createString("profileImg");

    public final EnumPath<com.example.market.domain.auth.constant.Role> role = createEnum("role", com.example.market.domain.auth.constant.Role.class);

    public final com.example.market.domain.shop.entity.QShop shop;

    public final EnumPath<com.example.market.domain.auth.constant.SocialType> socialType = createEnum("socialType", com.example.market.domain.auth.constant.SocialType.class);

    public final ListPath<com.example.market.domain.trade.entity.TradeItem, com.example.market.domain.trade.entity.QTradeItem> tradeItem = this.<com.example.market.domain.trade.entity.TradeItem, com.example.market.domain.trade.entity.QTradeItem>createList("tradeItem", com.example.market.domain.trade.entity.TradeItem.class, com.example.market.domain.trade.entity.QTradeItem.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final StringPath username = createString("username");

    public final ComparablePath<java.util.UUID> uuid = createComparable("uuid", java.util.UUID.class);

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.shop = inits.isInitialized("shop") ? new com.example.market.domain.shop.entity.QShop(forProperty("shop"), inits.get("shop")) : null;
    }

}

