package com.example.market.domain.auth.jwt;

import com.example.market.domain.auth.dto.PrincipalDetails;
import com.example.market.domain.auth.entity.RefreshToken;
import com.example.market.domain.auth.entity.User;
import com.example.market.domain.auth.repository.RefreshTokenRepository;
import com.example.market.domain.auth.repository.UserRepository;
import com.example.market.global.error.exception.ErrorCode;
import com.example.market.global.error.exception.GlobalCustomException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


// JWT 관련된 기능을 만드는 곳
@Slf4j
@Component
public class JwtTokenUtils {
    private final SecretKey secretKey;

    public JwtTokenUtils(
            @Value("${jwt.secret}")
            String secret,
            RefreshTokenRepository refreshTokenRepository, UserRepository userRepository,
            UserRepository userRepository1) {
        this.secretKey = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                SIG.HS256.key().build().getAlgorithm()
        );
    }

    // accessToken 으로 userId 정보 취득
    public String getEmail(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
                .get("email", String.class);
    }

    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public String generateAccessToken(String email) {
        Instant now = Instant.now();
        return Jwts.builder()
                .claim("email", email)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(60 * 1000)))
                .signWith(secretKey)
                .compact();
    }

    public RefreshToken generateRefreshToken(String accessToken, Long userId) {
        return new RefreshToken(UUID.randomUUID().toString(), accessToken, userId);

    }

    public Authentication getAuthentication(PrincipalDetails principalDetails) {
        // Authentication 객체를 생성 후 리턴
        return new UsernamePasswordAuthenticationToken(
                principalDetails, null, principalDetails.getAuthorities()
        );
    }

    public String getTokenFromCookie(HttpServletRequest request) {
        String authorization = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authorization")) {
                    authorization = cookie.getValue();
                    log.info("authorization 쿠키: {} ", authorization);
                }
            }
        } else {
            log.info("No cookies found");
        }
        return authorization;

    }


}
