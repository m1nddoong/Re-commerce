package com.example.market.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
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
    @NotNull
    private LocalDate birthday;
    @NotBlank
    private String phone;
}
