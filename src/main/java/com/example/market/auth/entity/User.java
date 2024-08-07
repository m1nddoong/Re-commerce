package com.example.market.auth.entity;



import com.example.market.trade.entity.TradeItem;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
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
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
// @SuperBuilder
@Table(name = "users")
public class User {
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
    @Setter
    private String businessNum; // 사업자 등록 번호
    @Setter
    private BusinessStatus businessStatus; // 사업자 전환 신청 상태
    @Setter
    private String authorities;

    @OneToMany(mappedBy = "user")
    private List<TradeItem> tradeItem;
}

