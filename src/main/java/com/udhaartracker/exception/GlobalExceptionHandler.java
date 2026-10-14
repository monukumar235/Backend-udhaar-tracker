package com.udhaartracker.exception;

import com.udhaartracker.dto.api.ApiResponse;
import com.udhaartracker.dto.api.ErrorData;
import com.udhaartracker.dto.api.MetaData;
import com.udhaartracker.emun.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthenticationException(AuthenticationException exception, HttpServletRequest request) {
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .data(null)
                .metaData(MetaData.builder()
                        .message(exception.getMessage())
                        .requestId(UUID.randomUUID().toString())
                        .success(false)
                        .timestamp(Instant.now().toString())
                        .error(ErrorData.builder()
                                .message(exception.getMessage())
                                .errorCode(exception.getErrorCode())
                                .build())
                        .build())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
    }

    @ExceptionHandler(BadCredentialException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentialException(BadCredentialException exception, HttpServletRequest request) {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .data(null)
                .metaData(MetaData.builder()
                        .message(exception.getMessage())
                        .requestId(UUID.randomUUID().toString())
                        .success(false)
                        .timestamp(Instant.now().toString())
                        .error(ErrorData.builder()
                                .message(exception.getMessage())
                                .errorCode(exception.getErrorCode())
                                .build())
                        .build())
                .build();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadRequestException(BadRequestException exception, HttpServletRequest request) {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .data(null)
                .metaData(MetaData.builder()
                        .timestamp(Instant.now().toString())
                        .success(false)
                        .message(exception.getMessage())
                        .requestId(UUID.randomUUID().toString())
                        .error(ErrorData.builder()
                                .errorCode(exception.getErrorCode())
                                .message(exception.getMessage())
                                .build())
                        .build())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNOtFoundException(ResourceNotFound exception, HttpServletRequest request) {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .data(null)
                .metaData(MetaData.builder()
                        .timestamp(Instant.now().toString())
                        .success(false)
                        .message(exception.getMessage())
                        .requestId(UUID.randomUUID().toString())
                        .error(ErrorData.builder()
                                .errorCode(exception.getErrorCode())
                                .message(exception.getMessage())
                                .build())
                        .build())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception exception, HttpServletRequest request) {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .data(null)
                .metaData(MetaData.builder()
                        .timestamp(Instant.now().toString())
                        .success(false)
                        .message("Somethings went wrong")
                        .requestId(UUID.randomUUID().toString())
                        .error(ErrorData.builder()
                                .errorCode(ErrorCode.INTERNAL_SERVER_ERROR)
                                .message(exception.getMessage())
                                .build())
                        .build())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }


}
