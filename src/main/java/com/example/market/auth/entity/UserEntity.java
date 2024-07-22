package com.example.market.auth.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
// @SuperBuilder
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //private String uuid;

    // 유저 네임은 Null 일 수 없고, 유일한 값이다
    @Column(nullable = false, unique = true)
    private String username;
    private String password;

    // private Integer age;
    // private String email;
    // private String phone;
    // private String role;
//    @Setter
//    private String profile;
}

