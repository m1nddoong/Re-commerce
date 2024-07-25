package com.example.market.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateMemberDto {
    private String username;
    private String password;
    private String passwordCheck;
}
