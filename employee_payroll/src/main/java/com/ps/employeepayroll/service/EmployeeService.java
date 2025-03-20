package com.ps.employeepayroll.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ps.employeepayroll.exception.EmployeeNotFoundException;
import com.ps.employeepayroll.model.Admin;
import com.ps.employeepayroll.model.Employee;
import com.ps.employeepayroll.repository.EmployeeRepository;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Employee findOrCreateGoogleUser(String email, String name) {
        Optional<Employee> optionalUser = employeeRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            // ✅ User exists, return the existing user
            return optionalUser.get();
        } else {
            // ✅ User does not exist, create a new user
            Employee newUser = new Employee();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setRole(Employee.Role.USER); // Default role as USER

            // ✅ Set a default password (Google users don't use passwords)
            newUser.setPassword(passwordEncoder.encode("google_auth_user"));

            // ✅ Save the new user to the database
            return employeeRepository.save(newUser);
        }
    }

    /**
     * Authenticates the user based on email and password.
     *
     * @param email    The user's email
     * @param password The user's entered password
     * @return The authenticated Employee object
     * @throws EmployeeNotFoundException if the user is not found or password is
     *                                   incorrect
     */
    public Employee authenticateUser(String email, String password) {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new EmployeeNotFoundException("User not found with email: " + email));

        // Check if the password matches
        if (!passwordEncoder.matches(password, employee.getPassword())) {
            throw new EmployeeNotFoundException("Invalid email or password");
        }

        return employee; // Successfully authenticated
    }

    public Optional<Employee> getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    public void registerEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    // Get all employees added by a specific admin
    public List<Employee> getEmployeesByAdmin(Admin admin) {
        return employeeRepository.findByAdmin(admin);
    }

    // Create an employee (similar to register but can be used for admin functions)
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

}
