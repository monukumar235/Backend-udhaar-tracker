package com.udhaartracker.dto.api;

import com.udhaartracker.emun.ErrorCode;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ErrorData
{
    private ErrorCode errorCode;
    private  String message;
}
