package com.example.market.auth.repo;

import com.example.market.auth.entity.BusinessStatus;
import com.example.market.auth.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 멀티 파트 파일 저장 시 사용자 이름으로 경로를 생성하기 위함
    Optional<User> findUserByUuid(UUID uuid);

    List<User> findAllByBusinessStatus(BusinessStatus businessStatus);

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    void deleteUserByUuid(UUID uuid);
}
