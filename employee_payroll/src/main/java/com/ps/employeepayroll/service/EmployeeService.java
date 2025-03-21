package com.ps.employeepayroll.service;

import com.ps.employeepayroll.exception.EmployeeNotFoundException;
import com.ps.employeepayroll.model.Employee;
import com.ps.employeepayroll.model.Role;
import com.ps.employeepayroll.repository.AdminRepository;
import com.ps.employeepayroll.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Employee registerUser(Employee employee) {
        // Encrypt password
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        // Check if the employee is an admin and handle accordingly
        if ("ADMIN".equals(employee.getRole())) {
            // Handle Admin-specific logic if needed (you can save to a different repository
            // or table if required)
            return adminRepository.save(employee);
        } else {
            // Default to employee if role is USER
            return employeeRepository.save(employee);
        }
    }

    public Optional<Employee> authenticate(String email, String password) {
        Optional<Employee> employee = employeeRepository.findByEmail(email);
        if (employee.isPresent() && passwordEncoder.matches(password, employee.get().getPassword())) {
            return employee;
        }
        return Optional.empty();
    }

    public Employee authenticateUser(String email, String rawPassword) throws Exception {
        Optional<Employee> userOpt = employeeRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            Employee user = userOpt.get();
            // ✅ Compare encrypted password
            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                return user; // Successfully authenticated
            }
        }

        throw new Exception("Invalid credentials");
    }

    public Employee findOrCreateGoogleUser(String email, String name) {
        Optional<Employee> userOpt = employeeRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            return userOpt.get();
        }

        // If user doesn't exist, create a new one
        Employee newUser = new Employee();
        newUser.setEmail(email);
        newUser.setName(name);
        newUser.setRole(Role.USER); // Default role for Google users
        newUser.setPassword(""); // No password needed for OAuth users
        return employeeRepository.save(newUser);
    }

    public Optional<Employee> findByEmail(String email) {
        return Optional.ofNullable(employeeRepository.findByEmail(email)
                .orElseThrow(() -> new EmployeeNotFoundException("User not found")));
    }

    public void registerAdmin(Employee employee) {
        // Save admin logic
        adminRepository.save(employee); // Assuming you have an Admin table/repository
    }

    public void registerEmployee(Employee employee) {
        // Save employee logic
        employeeRepository.save(employee); // Assuming you have an Employee table/repository
    }

    public void addEmployee(Employee employee) {
        employeeRepository.save(employee); // Save the employee to the repository
    }

}
