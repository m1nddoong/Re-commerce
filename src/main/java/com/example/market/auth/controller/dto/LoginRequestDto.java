package com.example.market.auth.controller.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
