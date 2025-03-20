package com.ps.employeepayroll.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {

    @Id
    private String adminId; // Unique ID for the admin

    private String name; // Name of the admin
    private String email; // Email of the admin
    private String phoneNumber; // Phone number of the admin
    private String profilePic; // Profile picture URL of the admin
    private String password; // Password for authentication
    private String role = "ADMIN"; // Role is always ADMIN for this entity

    // One-to-Many relationship with Employee (Admin can add multiple employees)
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Employee> employees = new ArrayList<>();

    // Method to add an employee
    public void addEmployee(Employee employee) {
        employee.setAdmin(this); // Set the admin for the employee
        this.employees.add(employee); // Add the employee to the admin's list
    }

    // Method to set salary for an employee
    public void setEmployeeSalary(Employee employee, double salary) {
        employee.setSalary(salary); // Set the salary for the employee
    }
}