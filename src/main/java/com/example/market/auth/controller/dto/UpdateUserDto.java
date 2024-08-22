package com.example.market.auth.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class UpdateUserDto {
    @NotBlank
    private String nickname;
    @NotBlank
    private String username;
    @NotBlank
    private Integer age;
    @NotBlank
    private String phone;
}
