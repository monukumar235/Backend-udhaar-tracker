package com.udhaartracker.service;

import com.udhaartracker.dto.auth.OtpVerificationRequest;
import com.udhaartracker.dto.auth.OtpVerificationResponse;
import com.udhaartracker.dto.auth.RegistrationRequest;
import com.udhaartracker.emun.ErrorCode;
import com.udhaartracker.emun.OtpTypes;
import com.udhaartracker.entity.OtpEntity;
import com.udhaartracker.entity.User;
import com.udhaartracker.exception.BadRequestException;
import com.udhaartracker.exception.ResourceNotFound;
import com.udhaartracker.repository.OtpRepository;
import com.udhaartracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OtpRepository otpRepository;

    @Value("${email.from}")
    private String from;

    @Autowired
    private TemplateEngine templateEngine;

    private final SecureRandom secureRandom = new SecureRandom();

    @Transactional
    public void registerUser(RegistrationRequest registrationRequest) {

        User user = userRepository.findByEmail(registrationRequest.getEmail());

        if(user!=null && user.getVerified().equals(true)){
            throw new BadRequestException("User already exist with this email id", ErrorCode.USER_ALREADY_EXISTS);
        }

        if(user==null){
            user = new User();
            user.setEmail(registrationRequest.getEmail());
            user.setFullName(registrationRequest.getFullName());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setVerified(false);
            userRepository.save(user);
        }
        else {
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setFullName(registrationRequest.getFullName());
            userRepository.save(user);
        }

        String otp = String.format(
                "%06d",
                secureRandom.nextInt(1_000_000)
        );

        OtpEntity otpEntity = new OtpEntity();

        otpEntity.setUser(user);
        otpEntity.setAttempts(0);
        otpEntity.setExipredAt(LocalDateTime.now().plusMinutes(5));
        otpEntity.setOtpHash(passwordEncoder.encode(otp));
        otpEntity.setIsUsed(false);
        otpEntity.setOtpType(OtpTypes.EMAIL_VERIFICATION.name());
        otpRepository.save(otpEntity);


        boolean sendEmail = emailService.sendEmail(user.getEmail(),from,"UdhaarTracker - Registration Verification",getTextMessage(otp,registrationRequest.getFullName(),"email/registration_verification_otp"));

        if(!sendEmail){
            throw new BadRequestException(
                    "Failed to send varification mail to your email please try again.",
                    ErrorCode.VALIDATION_ERROR
            );
        }
    }

    public OtpVerificationResponse verifyOtp(OtpVerificationRequest request){

        User user = userRepository.findByEmail(request.getEmail());

        if(user == null){
            throw new ResourceNotFound("User not found with this email", ErrorCode.USER_NOT_FOUND);
        }

        OtpEntity otpEntity = otpRepository.findTopByUserAndOtpTypeOrderByCreatedAtDesc(user, OtpTypes.EMAIL_VERIFICATION.toString());

        if (otpEntity == null){
            throw new ResourceNotFound("Invalid Verification code",ErrorCode.VALIDATION_ERROR);
        }

        if(otpEntity.getIsUsed()==true){
            throw new BadRequestException("Otp is already used",ErrorCode.VALIDATION_ERROR);
        }

        if(otpEntity.getAttempts()>=5){
            throw new BadRequestException("Maximum number of attempts reached",ErrorCode.VALIDATION_ERROR);
        }

        if(otpEntity.getExipredAt().isBefore(LocalDateTime.now())){
            throw new BadRequestException("Otp has been expired",ErrorCode.VALIDATION_ERROR);
        }

        if(!passwordEncoder.matches(request.getOtp(), otpEntity.getOtpHash())){
            otpEntity.setIsUsed(true);
            otpEntity.setAttempts(otpEntity.getAttempts() + 1);
            throw new BadRequestException("Invalid OTP",ErrorCode.VALIDATION_ERROR);
        }

        user.setVerified(true);
        userRepository.save(user);

        otpEntity.setIsUsed(true);
        otpRepository.save(otpEntity);

        OtpVerificationResponse otpVerificationResponse = new OtpVerificationResponse();
        otpVerificationResponse.setEmail(user.getEmail());
        otpVerificationResponse.setName(user.getFullName());

        return  otpVerificationResponse;
    }


    private String getTextMessage(String otp, String fullName, String templateType) {

        Context context = new Context();
        context.setVariable(
                "name",
                fullName
        );
        context.setVariable("otp",otp);
        return templateEngine.process(templateType, context);
    }
}
