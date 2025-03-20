package com.ps.employeepayroll.controller;

import com.ps.employeepayroll.model.Salary; // Change the import statement
import com.ps.employeepayroll.service.SalaryService; // Change the import statement
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salaries")
public class SalaryController {
    @Autowired
    private SalaryService salaryService;

    @GetMapping("/")
    public List<Salary> getAllSalaries() {
        return salaryService.getAllSalaries();
    }

}
