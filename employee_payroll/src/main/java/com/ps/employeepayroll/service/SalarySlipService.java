package com.ps.employeepayroll.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ps.employeepayroll.exception.SalarySlipNotFoundException;
import com.ps.employeepayroll.model.SalarySlip;
import com.ps.employeepayroll.repository.SalarySlipRepository;

import java.util.List;

@Service
public class SalarySlipService {

    @Autowired
    private SalarySlipRepository salarySlipRepository;

    // View all salary slips
    public List<SalarySlip> getAllSalarySlips() {
        return salarySlipRepository.findAll();
    }

    // View salary slip by Employee ID
    public SalarySlip getSalarySlipByEmployeeId(Long employeeId) {
        return salarySlipRepository.findByEmployee_EmployeeId(employeeId)
                .orElseThrow(
                        () -> new SalarySlipNotFoundException("Salary slip not found for Employee ID: " + employeeId));
    }

}
