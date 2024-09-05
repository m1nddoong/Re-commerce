package com.example.market.domain.auth.entity;



import com.example.market.domain.auth.constant.BusinessStatus;
import com.example.market.domain.auth.constant.Role;
import com.example.market.domain.used_trade.entity.TradeItem;
import com.example.market.global.common.BaseEntity;
import com.example.market.domain.shop.entity.Shop;
import com.example.market.domain.auth.constant.SocialType;
import io.hypersistence.utils.hibernate.id.Tsid;
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
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @Tsid
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID uuid;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    private String username;
    private String phone;
    private String nickname;
    private LocalDate birthday;
    private String profileImg;
    private String businessNum; // 사업자 등록 번호
    private Role role;

    @Enumerated(EnumType.STRING)
    private BusinessStatus businessStatus; // 사업자 전환 신청 상태

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @OneToMany(mappedBy = "user")
    private List<TradeItem> usedTradeItem;

    @OneToOne(mappedBy = "user")
    private Shop shop;
}

