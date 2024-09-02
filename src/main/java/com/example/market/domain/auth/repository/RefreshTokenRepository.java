package com.example.market.domain.auth.repository;

import com.example.market.domain.auth.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    // Optional<RefreshToken> findByUuid(String token);
    // Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
