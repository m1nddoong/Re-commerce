package com.example.market.unittest;


import com.example.market.domain.auth.entity.Tsid;
import com.example.market.domain.auth.entity.Tsid.FactorySupplierCustom;
import com.example.market.domain.auth.entity.User;
import com.example.market.domain.auth.repository.UserRepository;
import com.example.market.global.config.JpaConfig;
import com.github.f4b6a3.tsid.TsidFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
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
@Import(JpaConfig.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;


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


    @Test
    @DisplayName("싱글톤 테스트")
    public void testSingletonTest() {
        Tsid.FactorySupplierCustom instance1 = FactorySupplierCustom.INSTANCE;
        TsidFactory factory1 = instance1.get();
        Tsid.FactorySupplierCustom instance2 = FactorySupplierCustom.INSTANCE;
        TsidFactory factory2 = instance2.get();

        System.out.println("factory1 = " + factory1);
        System.out.println("factory2 = " + factory2);

        assertSame(factory1, factory2);
    }


    @Test
    @DisplayName("멀티 스레드 환경에서 TsidFactory 싱글톤 테스트")
    public void testTsidFactorySingletonInMultiThread() throws InterruptedException {
        Set<TsidFactory> factories = ConcurrentHashMap.newKeySet();
        // 테스트할 스레드 수
        int threadCount = 10;

        // 스레드 풀 생성
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                Tsid.FactorySupplierCustom instance = FactorySupplierCustom.INSTANCE;
                TsidFactory factory = instance.get();
                factories.add(factory);
            });
        }

        // 스레드 풀 종료 대기
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        // 모든 스레드에서 가져온 TsidFactory가 동일한 인스턴스인지 검증
        assertEquals(1, factories.size());
    }


    @Test
    @DisplayName("멀티 스레드 유저 생성 테스트")
    public void testSingletonMultiThreadTest() throws InterruptedException {
        Set<Long> setIdList = ConcurrentHashMap.newKeySet();
        // 테스트할 스레드 수
        int threadCount = 10;

        // 스레드 풀 생성
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                User user = new User();
                userRepository.save(user);
                setIdList.add(user.getId());
            });
        }

        // 스레드 풀 종료 대기
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("setList.size() =" + setIdList.size());
        assertEquals(setIdList.size(), threadCount);

    }
}

