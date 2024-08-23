package com.example.market.domain.user.entity;



import com.example.market.domain.user.constant.BusinessStatus;
import com.example.market.domain.user.constant.Role;
import com.example.market.global.common.BaseEntity;
import com.example.market.domain.shop.entity.Shop;
import com.example.market.domain.trade.entity.TradeItem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    // @Tsid
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid;

    @Column(nullable = false, unique = true)
    private String email;
    private String password;

    @Setter
    private String username;
    @Setter
    private String nickname;
    @Setter
    private Integer age;
    @Setter
    private String phone;
    @Setter
    private String profileImg;

    @OneToMany(mappedBy = "user")
    private List<TradeItem> tradeItem;

    @Setter
    private String businessNum; // 사업자 등록 번호
    @Setter
    @Enumerated(EnumType.STRING)
    private BusinessStatus businessStatus; // 사업자 전환 신청 상태

    @Setter
    @Builder.Default
    // @Enumerated(EnumType.STRING)
    private String authorities = Role.INACTIVE_USER.getRoles();

    @Setter
    @OneToOne(mappedBy = "user")
    private Shop shop;

}

