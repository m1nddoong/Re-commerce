package com.example.market.domain.user.jwt;

import com.example.market.domain.user.dto.PrincipalDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
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
            String secret
    ) {
        this.secretKey = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                SIG.HS256.key().build().getAlgorithm()
        );
    }

    public String getEmail(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
                .get("email", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration()
                .before(new Date());
    }

    public String createJwt(String email, TokenType tokenType) {
        Instant now = Instant.now();
        return Jwts.builder()
                .claim("email", email)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(tokenType.getTokenValidMillis())))
                .signWith(secretKey)
                .compact();
    }

    public Authentication getAuthentication(PrincipalDetails principalDetails) {
        // Authentication 객체를 생성 후 리턴
        return new UsernamePasswordAuthenticationToken(
                principalDetails, null, principalDetails.getAuthorities()
        );
    }


}
