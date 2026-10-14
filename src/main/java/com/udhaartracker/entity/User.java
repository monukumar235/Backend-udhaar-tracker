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
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "full_name", nullable = false,length = 120)
    private String fullName;

    @Column(name = "email", nullable = false,length = 225, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false,length = 225)
    private String password;

    @Column(name = "status", nullable = false,length = 20)
    private String status = "ACTIVE";

    @Column(name = "token_version", nullable = false)
    private Integer tokenVersion=0;

    @Column(name = "is_verified")
    private Boolean verified = false;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<BusinessMemberShip> memberShips = new ArrayList<>();
}
