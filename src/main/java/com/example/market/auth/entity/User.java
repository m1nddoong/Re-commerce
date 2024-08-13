package com.example.market.auth.entity;



import com.example.market.auth.constant.BusinessStatus;
import com.example.market.auth.constant.Role;
import com.example.market.common.BaseEntity;
import com.example.market.shop.entity.Shop;
import com.example.market.trade.entity.TradeItem;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @Tsid
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

