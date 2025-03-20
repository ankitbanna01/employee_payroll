package com.ps.employeepayroll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.ps.employeepayroll.model.Admin;
import com.ps.employeepayroll.model.Employee;
import com.ps.employeepayroll.service.AdminService;
import com.ps.employeepayroll.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private EmployeeService employeeService;

    // Create a new admin (Only accessible by authenticated users)
    @PostMapping("/")
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminService.createAdmin(admin);
    }

    // Add a new employee (Only accessible by the admin who is logged in)
    @PostMapping("/employee")
    @PreAuthorize("hasRole('ADMIN')") // Only ADMIN can add employees
    public Employee addEmployee(@RequestBody Employee employee) {
        // Get the currently logged-in admin
        String adminEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Admin admin = adminService.getAdminByEmail(adminEmail);

        // Set the admin for the employee
        employee.setAdmin(admin);
        return employeeService.createEmployee(employee);
    }

    // Get all employees added by the logged-in admin
    @GetMapping("/employees")
    @PreAuthorize("hasRole('ADMIN')") // Only ADMIN can view their employees
    public List<Employee> getEmployeesByAdmin() {
        // Get the currently logged-in admin
        String adminEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Admin admin = adminService.getAdminByEmail(adminEmail);

        // Return employees belonging to this admin
        return employeeService.getEmployeesByAdmin(admin);
    }
}