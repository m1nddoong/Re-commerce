package com.example.market.global.util;

import com.example.market.global.jwt.TokenType;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Base64;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
    // 요청값(이름, 값, 만료 기간)을 바탕으로 쿠키 추가
    public static Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        // cookie.setSecure(true); // https 에서만 쿠키를 사용할 수 있도록 설정
        cookie.setMaxAge((int) TokenType.ACCESS.getTokenValidMillis());
        cookie.setHttpOnly(true);
        return cookie;
    }

    // 쿠키의 이름을 입력받아 쿠키 삭제

    // 객체를 직렬화해 쿠키의 값으로 변환

    // 쿠키를 역직렬화해 객체로 변환

}
