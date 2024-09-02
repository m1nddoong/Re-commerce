package com.example.market.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
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
public class CreateUserDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String passwordCheck;
    private String username;
    private String phone;
    private String nickname;
    private LocalDate birthday;
}
