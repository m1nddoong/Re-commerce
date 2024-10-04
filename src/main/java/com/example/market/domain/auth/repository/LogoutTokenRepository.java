package com.example.market.domain.auth.repository;


import com.example.market.domain.auth.entity.LogoutToken;
import org.springframework.data.repository.CrudRepository;


public interface LogoutTokenRepository extends CrudRepository<LogoutToken, String> {
    boolean existsByAccessToken(String accessToken);
}
