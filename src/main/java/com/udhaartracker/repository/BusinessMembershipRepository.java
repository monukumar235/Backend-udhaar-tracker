package com.udhaartracker.repository;

import com.udhaartracker.entity.BusinessMemberShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusinessMembershipRepository extends JpaRepository<BusinessMemberShip, UUID> {

    List<BusinessMemberShip> findByUser_Id(UUID userId);

    List<BusinessMemberShip> findByBusiness_Id(UUID businessId);

    Optional<BusinessMemberShip> findByBusiness_IdAndUser_Id(UUID businessId, UUID userId);

    boolean existsByBusiness_IdAndUser_Id(UUID businessId, UUID userId);
}
