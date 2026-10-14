package com.udhaartracker.controller;

import com.udhaartracker.dto.api.ApiResponse;
import com.udhaartracker.dto.auth.LoginRequest;
import com.udhaartracker.dto.auth.OtpVerificationRequest;
import com.udhaartracker.dto.auth.OtpVerificationResponse;
import com.udhaartracker.dto.auth.RegistrationRequest;
import com.udhaartracker.service.AuthService;
import com.udhaartracker.utils.ResponseUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/v1/register")
    public ResponseEntity<ApiResponse<String>> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest){
        authService.registerUser(registrationRequest);
        return ResponseUtils.getSuccessResponse("Otp has been sent to your email address");
    }

    @PostMapping("/v1/verify-otp")
    public ResponseEntity<ApiResponse<OtpVerificationResponse>> verifyOtp(@Valid @RequestBody OtpVerificationRequest otpVerificationRequest){
        OtpVerificationResponse otpVerificationResponse = authService.verifyOtp(otpVerificationRequest);
        return ResponseUtils.getSuccessResponse(otpVerificationResponse);
    }
}
