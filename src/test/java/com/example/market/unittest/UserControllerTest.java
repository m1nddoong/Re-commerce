package com.example.market.unittest;



import com.example.market.auth.controller.UserController;
import com.example.market.auth.dto.CreateUserDto;
import com.example.market.auth.dto.UserDto;
import com.example.market.auth.service.UserService;
import com.example.market.util.JsonUtil;
import java.io.IOException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    // 이 테스트 클래스의 개별 클레스 전에 실행할 코드
    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();
    }

    @Test
    @DisplayName("CreateUserDto를 표현한 JSON 요청을 보내면 UserDto로 표현한 JSON을 응답")
    public void testSignUp() throws Exception {
        String email = "Brad@naver.com";
        String plainPassword = "1234";
        CreateUserDto requestDto = CreateUserDto.builder()
                .email(email)
                .password(plainPassword)
                .passwordCheck(plainPassword)
                .build();
        UserDto responseDto = UserDto.builder()
                .id(1L)
                .email(email)
                .build();
        when(userService.signUp(any()))
                .thenReturn(responseDto);

        // when
        ResultActions result = mockMvc.perform(
                post("/users/sign-up")
                        .content(JsonUtil.toJson(requestDto))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        // 응답의 코드가 2xx
        // 내용이 JSON
        // email 이 변하지 않았따.
        // id 가 null 이 아니다.
        result.andExpectAll(
                // 2xx번대(성공) 상태코드
                status().is2xxSuccessful(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.email", is(email)),
                jsonPath("$.id", notNullValue())
        );
    }
}
