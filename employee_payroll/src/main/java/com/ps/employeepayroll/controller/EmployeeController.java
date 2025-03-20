package com.ps.employeepayroll.controller;

import com.ps.employeepayroll.exception.EmployeeNotFoundException;
import com.ps.employeepayroll.model.Employee;
import com.ps.employeepayroll.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * ✅ View salary (Only accessible by the employee themselves)
     */
    @GetMapping("/salary")
    public ResponseEntity<Double> viewSalary(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        Employee employee = employeeService.getEmployeeByEmail(email)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        return ResponseEntity.ok(employee.getSalary());
    }

    /**
     * ✅ Download salary slip (Only accessible by the employee themselves)
     */
    @GetMapping("/salary-slip")
    public ResponseEntity<String> downloadSalarySlip(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        Employee employee = employeeService.getEmployeeByEmail(email)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        return ResponseEntity.ok(employee.getSalarySlipUrl());
    }
}
