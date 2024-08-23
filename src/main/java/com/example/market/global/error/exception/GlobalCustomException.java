package com.example.market.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GlobalCustomException extends RuntimeException {
    private ErrorCode errorCode;
}
