package com.udhaartracker.exception;

import com.udhaartracker.emun.ErrorCode;

public class BadCredentialException extends RuntimeException{

    private final ErrorCode errorCode;

    public BadCredentialException(ErrorCode errorCode,String message) {
        super(message);
        this.errorCode = errorCode;
    }
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
