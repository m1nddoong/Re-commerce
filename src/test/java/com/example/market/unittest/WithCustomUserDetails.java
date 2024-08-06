package com.example.market.unittest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;



// 테스트에 사용자 인증을 설정하기 위한 커스텀 애너테이션
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithCustomUserDetailsFactory.class)
public @interface WithCustomUserDetails {
    String username () default "user";

    String password() default "password";
    String[] authorities() default {"ROLE_USER"};
}

