package com.udhaartracker.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    @NotBlank(message = "Full name is required")
    @Size(max = 120,message = "Full name cannot exceed 120 characters")
    private String fullName;

    @NotBlank(message = "email is required")
    @Size(max = 225,message = "email cannot exceed 225 characters")
    @Email(message = "please provider a valid email")
    private  String email;

    @NotBlank(message = "password is required")
    @Size(min=8,max = 72,message = "password must be between 8 and 72 characters")
    private String password;
}
