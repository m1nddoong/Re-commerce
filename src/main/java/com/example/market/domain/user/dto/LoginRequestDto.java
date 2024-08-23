package com.example.market.domain.user.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
