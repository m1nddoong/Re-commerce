package com.example.market.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
// 에러 응답 형식 정의
public class ErrorResponse {
    private int status;
    private final String message;
    private final String code;

    public ErrorResponse(GlobalErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }

}
