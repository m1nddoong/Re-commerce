package com.example.market.auth.entity;

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

    private static final long serialVersionUID = -2082198959L;

    public static final QUser user = new QUser("user");

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final StringPath authorities = createString("authorities");

    public final StringPath businessNum = createString("businessNum");

    public final EnumPath<User.BusinessStatus> businessStatus = createEnum("businessStatus", User.BusinessStatus.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final StringPath profileImg = createString("profileImg");

    public final ListPath<com.example.market.trade.entity.TradeItem, com.example.market.trade.entity.QTradeItem> tradeItem = this.<com.example.market.trade.entity.TradeItem, com.example.market.trade.entity.QTradeItem>createList("tradeItem", com.example.market.trade.entity.TradeItem.class, com.example.market.trade.entity.QTradeItem.class, PathInits.DIRECT2);

    public final StringPath username = createString("username");

    public final ComparablePath<java.util.UUID> uuid = createComparable("uuid", java.util.UUID.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

