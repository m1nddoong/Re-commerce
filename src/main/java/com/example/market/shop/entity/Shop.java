package com.example.market.shop.entity;


import com.example.market.auth.entity.User;
import com.example.market.common.BaseEntity;
import com.example.market.shop.constant.ShopCategory;
import com.example.market.shop.constant.ShopStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Repository;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Shop extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String name;
    @Setter
    private String introduction;
    @Setter
    @Enumerated(EnumType.STRING)
    private ShopCategory category;
    @Setter
    @Enumerated(EnumType.STRING)
    private ShopStatus status = ShopStatus.PREPARING;
    @Setter
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    private String address; // 주소
    @Setter
    private String coordinates; // 좌표

    @Builder.Default
    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    private final List<ShopItem> items = new ArrayList<>();
}
