package com.example.market.shop.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItemSubCategory is a Querydsl query type for ItemSubCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItemSubCategory extends EntityPathBase<ItemSubCategory> {

    private static final long serialVersionUID = -291852553L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItemSubCategory itemSubCategory = new QItemSubCategory("itemSubCategory");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QItemCategory itemCategory;

    public final StringPath name = createString("name");

    public QItemSubCategory(String variable) {
        this(ItemSubCategory.class, forVariable(variable), INITS);
    }

    public QItemSubCategory(Path<? extends ItemSubCategory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItemSubCategory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItemSubCategory(PathMetadata metadata, PathInits inits) {
        this(ItemSubCategory.class, metadata, inits);
    }

    public QItemSubCategory(Class<? extends ItemSubCategory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.itemCategory = inits.isInitialized("itemCategory") ? new QItemCategory(forProperty("itemCategory")) : null;
    }

}

