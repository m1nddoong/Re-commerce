package com.example.market.auth.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class UpdateUserDto {
    private String nickname;
    private String username;
    private Integer age;
    private String phone;
}
