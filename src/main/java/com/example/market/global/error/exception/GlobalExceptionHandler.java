package com.example.market.global.error.exception;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 런타임 에러
    @ExceptionHandler(GlobalCustomException.class)
    public ResponseEntity<?> handleGlobalCustomException(GlobalCustomException e) {
        log.error("Error occurs {}", e.toString());
        return buildResponseEntity(e.getErrorCode());
    }

    // jwt 에러
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> handleExpiredJwtException(ExpiredJwtException e) {
        log.error("Expired JWT Token: {}", e.getMessage());

        GlobalCustomException globalCustomException = new GlobalCustomException(ErrorCode.ACCESS_TOKEN_EXPIRED, e);
        return handleGlobalCustomException(globalCustomException);
    }

    private ResponseEntity<?> buildResponseEntity(ErrorCode errorCode) {
        // HTTP 응답의 헤더 설정
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");
        ErrorResponse errorResponse = new ErrorResponse(errorCode);
        HttpStatus status = HttpStatus.resolve(errorCode.getStatus());
        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        // HTTP 응답을 구성 (응답 본문, 헤더, 상태코드)
        return new ResponseEntity<>(errorResponse, resHeaders, status);
    }
}
