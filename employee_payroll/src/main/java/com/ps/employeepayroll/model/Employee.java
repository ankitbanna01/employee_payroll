package com.ps.employeepayroll.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long employeeId; // Primary key column

    private String name;
    @Column(unique = true) // Enforce uniqueness at the database level
    private String email;
    private String phoneNumber;
    private String profilePic;
    private String designation;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private SalarySlip salarySlip;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    public void setPassword(String password) {
        this.password = password; // Store plain text temporarily
    }
}