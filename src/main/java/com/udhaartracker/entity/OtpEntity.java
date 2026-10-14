package com.udhaartracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "otp")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OtpEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "otp_id")
    private UUID otpId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "otp_hash",nullable = false)
    private String otpHash;

    @Column(name = "expires_at",nullable = false)
    private LocalDateTime exipredAt;

    @Column(name = "attempts",nullable = false)
    private Integer attempts=0;

    @Column(name = "is_used",nullable = false)
    private Boolean isUsed=false;

    @Column(name = "reset_token_hash",nullable = false)
    private String resetTokenHash;

    @Column(name = "reset_token_expires_at",nullable = false)
    private LocalDateTime resetTokenExpiresAt;

    @Column(name = "otp_type")
    private String otpType;
}
