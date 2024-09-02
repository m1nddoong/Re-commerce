package com.example.market.domain.auth.dto;


import com.example.market.domain.auth.entity.User;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String uuid;
    private String email;
    private String password;
    private String username;
    private String phone;
    private String nickname;
    private LocalDate birthday;
    private String profileImg;
    private String businessNum;
    private String businessStatus;
    private String roles;

    // static factory method
    public static UserDto fromEntity(User entity) {
        return UserDto.builder()
                .id(entity.getId())
                .uuid(String.valueOf(entity.getUuid()))
                .email(entity.getEmail())
                .password(entity.getPassword())
                .nickname(entity.getNickname())
                .username(entity.getUsername())
                .birthday(entity.getBirthday())
                .phone(entity.getPhone())
                .profileImg(entity.getProfileImg())
                .businessNum(entity.getBusinessNum())
                .businessStatus(String.valueOf(entity.getBusinessStatus()))
                .roles(entity.getRole().getRoles())
                .build();
    }

}