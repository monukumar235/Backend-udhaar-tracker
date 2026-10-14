package com.udhaartracker.repository;

import com.udhaartracker.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    List<Customer> findByBusiness_IdOrderByCreatedAtDesc(UUID businessId);

    List<Customer> findByBusiness_IdAndNameContainingIgnoreCase(UUID businessId,String name);

    Optional<Customer> findByIdAndBusiness_Id(UUID businessId, UUID id);
}
