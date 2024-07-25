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
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
// @SuperBuilder
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 유저 네임은 Null 일 수 없고, 유일한 값이다
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    private String username; // 이름
    private String nickname;
    private Integer age;
    private String phone;

    @Setter
    private String profileImg;
    @Setter
    @Builder.Default
    private String authorities = "INACTIVE_USER";
    @Setter
    private String businessNum;
}

