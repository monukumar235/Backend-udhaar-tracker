package com.udhaartracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;


@Entity
@Table(name = "business_memberships")
@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class BusinessMemberShip {

    @EmbeddedId
    private BusinessMembershipId id;

    @MapsId("businessId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(name = "role",nullable = false)
    private String role;

    @Column(name = "created_at",nullable = false,updatable = false)
    private Timestamp createdAt;
}
