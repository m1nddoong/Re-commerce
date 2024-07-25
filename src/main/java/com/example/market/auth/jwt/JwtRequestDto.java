package com.example.market.auth.jwt;

import lombok.Data;

@Data
public class JwtRequestDto {
    private String email;
    private String password;
}
