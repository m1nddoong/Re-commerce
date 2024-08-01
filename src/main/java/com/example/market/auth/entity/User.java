package com.example.market.auth.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.Collection;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
// @SuperBuilder
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

}

