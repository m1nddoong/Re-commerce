package com.example.market.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GlobalCustomException extends RuntimeException {
    private GlobalErrorCode globalErrorCode;
}
