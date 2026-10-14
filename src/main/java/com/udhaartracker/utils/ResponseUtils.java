package com.udhaartracker.utils;

import com.udhaartracker.dto.api.ApiResponse;
import com.udhaartracker.dto.api.ErrorData;
import com.udhaartracker.dto.api.MetaData;
import com.udhaartracker.emun.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

@Component
public class ResponseUtils {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> ResponseEntity<ApiResponse<T>> getSuccessResponse(T data) {

        ApiResponse<T> apiResponse = ApiResponse.<T>builder()
                .data(data)
                .metaData(getMetaForSuccess())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    public static MetaData getMetaForSuccess(){
        return MetaData.builder()
                .success(true)
                .message("Success")
                .timestamp(Instant.now().toString())
                .requestId(UUID.randomUUID().toString())
                .build();
    }

    public void writeErrorResponse(HttpServletResponse response, HttpStatus httpStatus, String message, ErrorCode errorCode) throws IOException {
        response.setStatus(httpStatus.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .data(null)
                .metaData(MetaData.builder()
                        .message(message)
                        .success(false)
                        .timestamp(Instant.now().toString())
                        .requestId(UUID.randomUUID().toString())
                        .error(ErrorData.builder()
                                .errorCode(errorCode)
                                .message(message)
                                .build())
                        .build())
                .build();
        response.getWriter().write(
                objectMapper.writeValueAsString(apiResponse)
        );
    }
}
