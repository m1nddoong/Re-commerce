package com.example.market.unittest;


import com.example.market.auth.domain.User;
import com.example.market.auth.repo.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

/**
 * [슬라이스 테스트]
 * : 슬라이스 테스트는 애플리케이션의 특정 계층 (예: 웹, 서비스, 데이터베이스) 를 테스트한다.
 * - 부분적 컨텍스트 로드
 *   - 테스트하는 계층에 필요한 빈만 로드한다. (@Autowired)
 *   - 보다 넓은 범위 : 테스트하는 계층의 여러 빈이 통합된 상태에서의 동작을 확인한다.
 *   - @WebMvcTest : 컨트롤러 웹 계층을 테스트하고, Mock 객체를 사용하여 서비스 레이어와의 상호작용을 검증
 *   - @DataJpaTest : 데이터베이스와 관련된 레포지토리 계층을 테스트
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

        // then
        assertEquals(email, result.getEmail());
        assertNotNull(result.getId());
    }



}

