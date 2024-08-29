package com.example.market.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
