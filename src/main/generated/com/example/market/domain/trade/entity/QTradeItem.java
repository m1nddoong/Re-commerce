package com.example.market.domain.trade.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTradeItem is a Querydsl query type for TradeItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTradeItem extends EntityPathBase<TradeItem> {

    private static final long serialVersionUID = -340862721L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTradeItem tradeItem = new QTradeItem("tradeItem");

    public final com.example.market.global.common.QBaseEntity _super = new com.example.market.global.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image = createString("image");

    //inherited
    public final BooleanPath isDelete = _super.isDelete;

    public final EnumPath<TradeItem.ItemStatus> itemStatus = createEnum("itemStatus", TradeItem.ItemStatus.class);

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.example.market.domain.user.entity.QUser user;

    public QTradeItem(String variable) {
        this(TradeItem.class, forVariable(variable), INITS);
    }

    public QTradeItem(Path<? extends TradeItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTradeItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTradeItem(PathMetadata metadata, PathInits inits) {
        this(TradeItem.class, metadata, inits);
    }

    public QTradeItem(Class<? extends TradeItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.example.market.domain.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

