package com.udhaartracker.exception;

import com.udhaartracker.emun.ErrorCode;

public class ResourceNotFound extends RuntimeException {

    public final ErrorCode errorCode;

    public ResourceNotFound(String message,ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

   public  ErrorCode getErrorCode() {
        return errorCode;
   }
}
