package com.udhaartracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @Column(name = "name", nullable = false,length = 120)
    private String name;

    @Column(name = "phone", nullable = false,length =20)
    private String phone;

    @Column(name = "email", nullable = false,length = 225)
    private String email;

    @Column(name = "status", nullable = false,length = 20)
    private String status = "Active";

}
