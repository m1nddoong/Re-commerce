package com.example.market.auth.repo;

import com.example.market.auth.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    // Optional<RefreshToken> findByUuid(String token);
    // Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
