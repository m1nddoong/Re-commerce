//package com.example.market.unittest;
//
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//import com.example.market.auth.dto.CreateUserDto;
//import com.example.market.auth.dto.UserDto;
//import com.example.market.auth.entity.User;
//import com.example.market.auth.repo.UserRepository;
//import com.example.market.auth.service.UserService;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//
//@SpringBootTest
//@Transactional
//public class UserServiceTest {
//    @Mock // 모조품
//    private UserRepository userRepository;
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @InjectMocks
//    private UserService userService;
//
//
//
//
//    @Test
//    @DisplayName("UserDto로 User 생성")
//    public void testCreateUser() {
//        // given
//        // 생성할 Uer
//        String email = "alex@naver.com";
//        String plainPassword = "1234";
//        String encodedPassword = passwordEncoder.encode(plainPassword);
//        User userIn = new User();
//        userIn.setEmail(email);
//        userIn.setPassword(encodedPassword);
//
//        Long userId = 1L;
//        User userOut = new User();
//        userOut.setId(userId);
//        userOut.setEmail(email);
//        userOut.setPassword(encodedPassword);
//
//        // Mcok 객체 (가짜 객체)의 행동 정의
//        when(userRepository.save(userIn)).thenReturn(userOut);
//
//        // when
//        // CreateUserDto에는 평문 비밀번호를 설정
//        CreateUserDto createUserDto = new CreateUserDto();
//        createUserDto.setEmail(email);
//        createUserDto.setPassword(plainPassword);
//        createUserDto.setPasswordCheck(plainPassword);
//        UserDto result = userService.signUp(createUserDto);
//
//        // then
//        assertEquals(userId, result.getId());
//        assertEquals(email, result.getEmail());
//    }
//
//}
