package com.example.market.domain.shop.entity;


import com.example.market.domain.auth.entity.User;
import com.example.market.domain.shop.constant.ShopStatus;
import com.example.market.global.common.BaseEntity;
import com.example.market.domain.shop.constant.ShopCategory;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.LastModifiedDate;

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
    private ShopCategory shopCategory;
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

    @LastModifiedDate
    @Setter
    private LocalDateTime lastTransactionDate;

    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    private final List<Item> items = new ArrayList<>();
}
