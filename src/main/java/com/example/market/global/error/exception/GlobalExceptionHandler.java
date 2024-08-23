package com.example.market.global.error.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalCustomException.class)
    public ResponseEntity<?> exceptionHandler(GlobalCustomException e) {
        log.error("Error occurs {}", e.toString());

        // HTTP 응답의 헤더 설정
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(errorCode);
        HttpStatus status = HttpStatus.resolve(errorCode.getStatus());
        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        // HTTP 응답을 구성 (응답 본문, 헤더, 상태코드)
        return new ResponseEntity<>(errorResponse, resHeaders, status);
    }
}
