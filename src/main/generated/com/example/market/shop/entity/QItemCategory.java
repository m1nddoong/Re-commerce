package com.example.market.shop.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItemCategory is a Querydsl query type for ItemCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItemCategory extends EntityPathBase<ItemCategory> {

    private static final long serialVersionUID = -1784972283L;

    public static final QItemCategory itemCategory = new QItemCategory("itemCategory");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<ItemSubCategory, QItemSubCategory> subCategories = this.<ItemSubCategory, QItemSubCategory>createList("subCategories", ItemSubCategory.class, QItemSubCategory.class, PathInits.DIRECT2);

    public QItemCategory(String variable) {
        super(ItemCategory.class, forVariable(variable));
    }

    public QItemCategory(Path<? extends ItemCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QItemCategory(PathMetadata metadata) {
        super(ItemCategory.class, metadata);
    }

}

