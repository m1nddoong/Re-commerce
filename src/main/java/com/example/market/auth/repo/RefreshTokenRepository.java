package com.example.market.auth.repo;

import com.example.market.auth.entity.RefreshToken;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

    // @Indexed 어노테이션을 사용한 accessToken 필드로 RefreshToken 객체를 조회하는 쿼리 메서드
    Optional<RefreshToken> findByAccessToken(String accessToken);
}
