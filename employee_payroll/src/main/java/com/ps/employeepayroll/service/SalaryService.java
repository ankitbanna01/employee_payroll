package com.ps.employeepayroll.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ps.employeepayroll.model.Salary;
import com.ps.employeepayroll.repository.SalaryRepository;

import java.util.List;

@Service
public class SalaryService {
    @Autowired
    private SalaryRepository salaryRepository;

    public List<Salary> getAllSalaries() {
        return salaryRepository.findAll();
    }
}
