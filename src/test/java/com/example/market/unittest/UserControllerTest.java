package com.example.market.unittest;



import com.example.market.domain.auth.controller.UserController;
import com.example.market.domain.auth.dto.CreateUserDto;
import com.example.market.domain.auth.dto.UserDto;
import com.example.market.domain.auth.service.PrincipalDetailsService;
import com.example.market.util.JsonUtil;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * [단위 테스트]
 * : 단위 테스트는 애플리케이션의 가장 작은 단위인 메서드나 클래스를 독립적으로 테스트 (개별 메서드 테스트용)
 * - Mock 객체 사용
 *   - PrincipalDetailsService 는 Mock 객체로 되어 있으며,
 *   - UserController 의 동작만 테스트한다.
 *   - 실제 데이터 베이스나, 전체 Spring Context 를 로드하지 않는다.
 */

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private PrincipalDetailsService principalDetailsService;

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
        // when(customUserService.signUp(any()))
           //     .thenReturn(responseDto);

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
