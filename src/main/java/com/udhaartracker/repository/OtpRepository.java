package com.udhaartracker.repository;

import com.udhaartracker.entity.OtpEntity;
import com.udhaartracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OtpRepository extends JpaRepository<OtpEntity, UUID> {

    OtpEntity findTopByUserAndOtpTypeOrderByCreatedAtDesc(User user, String type);
}
