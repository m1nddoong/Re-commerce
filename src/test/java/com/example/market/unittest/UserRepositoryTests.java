package com.example.market.unittest;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.market.auth.entity.User;
import com.example.market.auth.repo.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("사용자 생성 테스트")
    public void testSaveNew() {
        // given
        String username = "노홍철";
        User user = new User();
        user.setUsername(username);

        // when
        user = userRepository.save(user);

        // then
        // 주어진 값이 null 이 아닌지 검증
        assertNotNull(user.getId());
        // 주어진 두 값이 동일한지 검증
        assertEquals(username, user.getUsername());
    }
}
