package com.person.SpringSecurityJWTDemo001.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private long phone;
    private String gender;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Roles> roles;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private PersonSensitiveDetails sensitiveDetails;
}
