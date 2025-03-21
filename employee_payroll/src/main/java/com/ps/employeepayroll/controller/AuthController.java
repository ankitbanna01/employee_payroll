package com.ps.employeepayroll.controller;

import com.ps.employeepayroll.model.Employee;
import com.ps.employeepayroll.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(EmployeeService employeeService, BCryptPasswordEncoder passwordEncoder) {
        this.employeeService = employeeService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String index() {
        return "index"; // Redirects to the homepage
    }

    @GetMapping("/signup")
    public String showSignupPage() {
        return "signup"; // Renders the signup page
    }

    @PostMapping("/signup")
    public String processRegister(@Valid @ModelAttribute Employee employee,
            BindingResult bindingResult,
            HttpSession session,
            Model model) {
        System.out.println("Processing Employee Registration");

        // Validate form data
        if (bindingResult.hasErrors()) {
            return "signup"; // Return back to signup page if errors
        }

        // Encrypt the password before saving
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        // Save employee (either Admin or Employee)
        if (employee.getRole().equals("ADMIN")) {
            employeeService.registerAdmin(employee); // Method to save Admin
        } else {
            employeeService.registerEmployee(employee); // Method to save Employee
        }

        System.out.println("Employee saved: " + employee.getEmail());

        // Success message
        session.setAttribute("message", "Registration Successful! Please login.");

        // Redirect to login page after successful registration
        return "redirect:/auth/login";
    }

    @RequestMapping("/admin/addEmployee")
    public String addEmployee(@ModelAttribute Employee employee, HttpSession session, Model model) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword())); // Encrypt password before saving
        employeeService.registerEmployee(employee); // Save employee to DB
        session.setAttribute("message", "Employee added successfully");
        return "redirect:/admin_dashboard"; // Redirect to admin dashboard
    }

    // @GetMapping("/user/viewSalary")
    // public String viewSalary(HttpSession session, Model model) {
    // Employee loggedUser = (Employee) session.getAttribute("loggedUser");
    // if (loggedUser != null) {
    // Salary salary = employeeService.getSalary(loggedUser.getId());
    // model.addAttribute("salary", salary);
    // return "viewSalary"; // Render viewSalary page
    // }
    // return "redirect:/auth/login"; // Redirect to login if not logged in
    // }

}
