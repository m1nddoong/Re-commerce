package com.example.market.global.util;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
    // 요청값(이름, 값, 만료 기간)을 바탕으로 쿠키 추가
    public Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        // cookie.setSecure(true); // https 에서만 쿠키를 사용할 수 있도록 설정
        cookie.setMaxAge(60*1000);
        cookie.setHttpOnly(true);
        return cookie;
    }

    // 쿠키의 이름을 입력받아 쿠키 삭제
    public Cookie deleteCookie(String key) {
        Cookie cookie = new Cookie(key, null);
        cookie.setPath("/"); // 클라이언트 측에서 접근 가능한 쿠키의 경로 지정 -> 모든 경로에서 쿠키가 전송될 수 있음
        cookie.setMaxAge(0); // 쿠키 만료 시간을 0으로 설정하여 삭제
        return cookie;
    }

    // 객체를 직렬화해 쿠키의 값으로 변환

    // 쿠키를 역직렬화해 객체로 변환

}
