package com.example.market.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateMemberDto {
    private String email;
    private String password;
    private String passwordCheck;
    private String nickname;
    private String username;
    private Integer age;
    private String phone;
}
