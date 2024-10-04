package com.example.market.domain.auth.repository;

import com.example.market.domain.auth.constant.BusinessStatus;
import com.example.market.domain.auth.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByBusinessStatus(BusinessStatus businessStatus);

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
