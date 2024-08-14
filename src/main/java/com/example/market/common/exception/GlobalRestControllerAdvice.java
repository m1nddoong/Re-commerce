package com.example.market.common.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalRestControllerAdvice {

    @ExceptionHandler(GlobalExceptionHandler.class)
    public ResponseEntity<?> applicationHandler(GlobalExceptionHandler e) {
        log.error("Error occurs {}", e.toString());

        // 에러 응답 세팅
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("code", e.getCustomGlobalErrorCode().getCode());
        errorResponse.put("message", e.getCustomGlobalErrorCode().getMessage());

        return ResponseEntity.status(e.getCustomGlobalErrorCode().getStatus())
                .body(errorResponse);
    }
}
