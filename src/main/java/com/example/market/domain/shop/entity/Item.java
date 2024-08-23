package com.example.market.domain.shop.entity;

import com.example.market.global.common.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String name;
    @Setter
    private String img;
    @Setter
    private String description;
    @Setter
    private Integer price;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_category_id")
    @Setter
    private Category category; // 추가 가능

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_sub_category_id")
    @Setter
    private SubCategory subCategory; // 추가 가능

    @Setter
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<OrderItem> orders = new ArrayList<>();
}

