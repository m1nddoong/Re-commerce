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

@DataJpaTest
@ActiveProfiles("test") // application-test.yaml 과 같은 파일을 자동으로 찾음
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("새로운 User 추가")
    public void testCreateUser() {
        // given
        String email = "alex@naver.com";
        User user = new User(
                null,
                null,
                email,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        // when
        User result = userRepository.save(user);

        assertEquals(email, result.getEmail());
        assertNotNull(result.getId());
    }
}
