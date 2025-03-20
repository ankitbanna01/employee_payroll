package com.ps.employeepayroll.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ps.employeepayroll.exception.EmployeeNotFoundException;
import com.ps.employeepayroll.exception.SalarySlipNotFoundException;
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

    private static final double TAX_PERCENTAGE = 0.1; // 🔹 Move tax percentage to a constant

    public List<SalarySlip> getAllSalarySlips() {
        return salarySlipRepository.findAll();
    }

    public SalarySlip getSalarySlipByEmployeeId(String employeeId) {
        return salarySlipRepository.findByEmployee_EmployeeId(employeeId)
                .orElseThrow(
                        () -> new SalarySlipNotFoundException("Salary slip not found for Employee ID: " + employeeId));
    }

    public SalarySlip generateSalarySlip(String employeeId, double baseSalary) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + employeeId));

        SalarySlip salarySlip = new SalarySlip();
        salarySlip.setEmployee(employee);
        salarySlip.setBaseSalary(baseSalary);
        salarySlip.setTaxAmount(baseSalary * TAX_PERCENTAGE);
        salarySlip.setNetSalary(baseSalary - salarySlip.getTaxAmount());
        salarySlip.setGeneratedDate(LocalDate.now());

        return salarySlipRepository.save(salarySlip);
    }

    public SalarySlip updateSalarySlip(String employeeId, double newBaseSalary) {
        SalarySlip salarySlip = getSalarySlipByEmployeeId(employeeId);
        salarySlip.setBaseSalary(newBaseSalary);
        salarySlip.setTaxAmount(newBaseSalary * TAX_PERCENTAGE);
        salarySlip.setNetSalary(newBaseSalary - salarySlip.getTaxAmount());

        return salarySlipRepository.save(salarySlip);
    }
}
