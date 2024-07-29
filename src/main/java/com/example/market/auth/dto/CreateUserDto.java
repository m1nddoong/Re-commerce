package com.example.market.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
// "서비스를 이용하려면 닉네임, 이름, 연령대, 이메일, 전화번호 정보를 추가해야 한다."
// 위 요구사항을 회원가입 시의 필수요소로 포함
public class CreateUserDto {
    private String email; // ID
    private String password; // 비밀번호
    private String passwordCheck;
    private String nickname; // 닉네임
    private String username; // 성명
    private Integer age; // 나이
    private String phone; // 전화번호
    private String profileImg;
}
