package com.udhaartracker.dto.api;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MetaData {
    private String timestamp;
    private boolean success;
    private String message;
    private String requestId;
    private ErrorData error;
}
