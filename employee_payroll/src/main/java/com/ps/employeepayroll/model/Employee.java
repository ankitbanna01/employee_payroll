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
    private String employeeId; // Unique ID for the employee

    private String name;
    private String email;
    private String phoneNumber;
    private String profilePic;
    private String designation;
    private String password;

    @Enumerated(EnumType.STRING) // ✅ Store as String in DB but use Enum in Java
    @Column(nullable = false)
    private Role role = Role.USER; // ✅ Default role as EMPLOYEE

    private double salary;
    private String salarySlipUrl;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    public double viewSalary() {
        return this.salary;
    }

    public String downloadSalarySlip() {
        return this.salarySlipUrl;
    }

    // ✅ Define Enum Inside or Outside the Class
    public enum Role {
        USER, ADMIN
    }
}
