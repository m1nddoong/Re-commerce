package com.example.market.unittest;

import com.example.market.auth.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveNew() {
        // given

        // when

        // then
    }
}
