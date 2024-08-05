package com.example.market.unittest;


import com.example.market.auth.entity.User;
import com.example.market.auth.repo.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 레포지토리 단위 테스트
 */
@DataJpaTest
@ActiveProfiles("test") // application-test.yaml 과 같은 파일을 자동으로 찾음
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("새로운 User 추가에 성공하는 테스트")
    public void testCreateUser() {
        // given
        String email = "alex@naver.com";

        User user = User.builder()
                .email(email)
                .build();

        // when
        User result = userRepository.save(user);

        assertEquals(email, result.getEmail());
        assertNotNull(result.getId());
    }

}

