package com.ps.employeepayroll.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ps.employeepayroll.model.Employee;
import com.ps.employeepayroll.model.SalarySlip;
import com.ps.employeepayroll.repository.EmployeeRepository;
import com.ps.employeepayroll.repository.SalarySlipRepository;
import java.time.LocalDate;
import java.util.List;

@Service
public class SalarySlipService {

    @Autowired
    private SalarySlipRepository salarySlipRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<SalarySlip> getAllSalarySlips() {
        return salarySlipRepository.findAll();
    }

    public SalarySlip getSalarySlipByEmployeeId(Long employeeId) {
        return salarySlipRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Salary slip not found for Employee ID: " + employeeId));
    }

    public SalarySlip generateSalarySlip(Long employeeId, double baseSalary) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        SalarySlip salarySlip = new SalarySlip();
        salarySlip.setEmployee(employee);
        salarySlip.setBaseSalary(baseSalary);
        salarySlip.setTaxAmount(baseSalary * 0.1); // 10% Tax
        salarySlip.setNetSalary(baseSalary - salarySlip.getTaxAmount());
        salarySlip.setGeneratedDate(LocalDate.now());

        return salarySlipRepository.save(salarySlip);
    }

    public SalarySlip updateSalarySlip(Long employeeId, double newBaseSalary) {
        SalarySlip salarySlip = getSalarySlipByEmployeeId(employeeId);
        salarySlip.setBaseSalary(newBaseSalary);
        salarySlip.setTaxAmount(newBaseSalary * 0.1);
        salarySlip.setNetSalary(newBaseSalary - salarySlip.getTaxAmount());
        return salarySlipRepository.save(salarySlip);
    }
}