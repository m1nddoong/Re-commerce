package com.example.market.auth.entity;



import com.example.market.trade.entity.TradeItem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
// @SuperBuilder
// PostgreSQL 에서 테이블명을 user 로 사용할 수 없으므로 테이블명 변경
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID uuid;

    // 유저 네임은 Null 일 수 없고, 유일한 값이다
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

