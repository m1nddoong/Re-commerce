package com.example.market.auth.dto;


import com.example.market.auth.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String username;
    private Integer age;
    private String phone;


    public static MemberDto fromEntity(Member entity) {
        return MemberDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .nickname(entity.getNickname())
                .username(entity.getUsername())
                .age(entity.getAge())
                .phone(entity.getPhone())
                .build();
    }
}