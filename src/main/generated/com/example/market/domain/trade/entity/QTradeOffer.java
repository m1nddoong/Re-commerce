package com.example.market.domain.trade.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTradeOffer is a Querydsl query type for TradeOffer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTradeOffer extends EntityPathBase<TradeOffer> {

    private static final long serialVersionUID = -1971684880L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTradeOffer tradeOffer = new QTradeOffer("tradeOffer");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QTradeItem items;

    public final com.example.market.domain.user.entity.QUser offeringUser;

    public final EnumPath<TradeOffer.OfferStatus> offerStatus = createEnum("offerStatus", TradeOffer.OfferStatus.class);

    public QTradeOffer(String variable) {
        this(TradeOffer.class, forVariable(variable), INITS);
    }

    public QTradeOffer(Path<? extends TradeOffer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTradeOffer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTradeOffer(PathMetadata metadata, PathInits inits) {
        this(TradeOffer.class, metadata, inits);
    }

    public QTradeOffer(Class<? extends TradeOffer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.items = inits.isInitialized("items") ? new QTradeItem(forProperty("items"), inits.get("items")) : null;
        this.offeringUser = inits.isInitialized("offeringUser") ? new com.example.market.domain.user.entity.QUser(forProperty("offeringUser"), inits.get("offeringUser")) : null;
    }

}

