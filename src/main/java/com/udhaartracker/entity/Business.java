package com.udhaartracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "businesses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Business extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false, length = 160)
    private String name;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "ACTIVE";

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "business")
    private List<BusinessMemberShip> memberShips= new ArrayList<>();

    @OneToMany(mappedBy = "business",fetch = FetchType.LAZY)
    private List<Customer> customers = new ArrayList<>();

}
