package com.udhaartracker.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OtpVerificationRequest {

    @NotBlank(message = "Otp is required")
    @Pattern(
            regexp = "\\d{6}",
            message = "Otp must be of 6 digits"
    )
    private String otp;

    @NotBlank(message = "email is required")
    @Email
    private String email;
}
