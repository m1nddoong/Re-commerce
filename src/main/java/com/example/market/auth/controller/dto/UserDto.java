package com.example.market.auth.controller.dto;


import com.example.market.auth.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String uuid;
    private String email;
    private String password;
    @Setter
    private String nickname;
    @Setter
    private String username;
    @Setter
    private Integer age;
    @Setter
    private String phone;
    private String profileImg;
    private String businessNum;
    private String businessStatus;
    private String authorities;

    // static factory method
    public static UserDto fromEntity(User entity) {
        return UserDto.builder()
                .id(entity.getId())
                .uuid(String.valueOf(entity.getUuid()))
                .email(entity.getEmail())
                .password(entity.getPassword())
                .nickname(entity.getNickname())
                .username(entity.getUsername())
                .age(entity.getAge())
                .phone(entity.getPhone())
                .profileImg(entity.getProfileImg())
                .businessNum(entity.getBusinessNum())
                .businessStatus(String.valueOf(entity.getBusinessStatus()))
                .authorities(entity.getAuthorities())
                .build();
    }

}