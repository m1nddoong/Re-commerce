package com.example.market.auth.dto;


import com.example.market.auth.entity.Role;
import com.example.market.auth.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
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
    private Role role;


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
                .role(entity.getRole())
                .build();
    }

}