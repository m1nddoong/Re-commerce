package com.example.market.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// "서비스를 이용하려면 닉네임, 이름, 연령대, 이메일, 전화번호 정보를 추가해야 한다."
// 위 요구사항을 회원가입 시의 필수요소로 포함
public class CreateUserDto {
    private String email; // ID
    private String password; // 비밀번호
    private String passwordCheck;
}
