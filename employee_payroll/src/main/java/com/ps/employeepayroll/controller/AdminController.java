package com.ps.employeepayroll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ps.employeepayroll.model.Employee;
import com.ps.employeepayroll.service.EmployeeService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EmployeeService employeeService;

    // Admin can add an employee
    @PostMapping("/deshboard")
    @PreAuthorize("hasRole('ADMIN')") // Ensure only admin can access this endpoint
    public String addEmployee(@RequestBody Employee employee) {
        try {
            employeeService.addEmployee(employee); // Call EmployeeService to add employee
            return "Employee added successfully";
        } catch (Exception e) {
            return "Error adding employee: " + e.getMessage();
        }
    }
}
