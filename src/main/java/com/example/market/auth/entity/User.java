package com.example.market.auth.entity;


import com.example.market.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@Entity
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;

    // 유저 네임은 Null 일 수 없고, 유일한 값이다
    @Column(nullable = false, unique = true)
    private String userName;
    private String nickName;

    private Integer age;
    private String email;
    private String phone;
    private String role;
    @Setter
    private String profile;
}

