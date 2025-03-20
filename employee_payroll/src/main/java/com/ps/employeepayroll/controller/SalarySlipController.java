package com.ps.employeepayroll.controller;

import com.itextpdf.text.DocumentException;
import com.ps.employeepayroll.model.SalarySlip;
import com.ps.employeepayroll.service.SalarySlipService;
import com.ps.employeepayroll.utils.SalarySlipExporter;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/salary-slips")
public class SalarySlipController {

    @Autowired
    private SalarySlipService salarySlipService;

    @GetMapping
    public List<SalarySlip> getAllSalarySlips() {
        return salarySlipService.getAllSalarySlips();
    }

    @GetMapping("/{employeeId}")
    public SalarySlip getSalarySlip(@PathVariable Long employeeId) {
        return salarySlipService.getSalarySlipByEmployeeId(employeeId);
    }

    @PostMapping("/{employeeId}")
    public SalarySlip generateSalarySlip(@PathVariable Long employeeId, @RequestParam double baseSalary) {
        return salarySlipService.generateSalarySlip(employeeId, baseSalary);
    }

    @PutMapping("/{employeeId}")
    public SalarySlip updateSalarySlip(@PathVariable Long employeeId, @RequestParam double newBaseSalary) {
        return salarySlipService.updateSalarySlip(employeeId, newBaseSalary);
    }

    @GetMapping("/export/{employeeId}")
    public void exportSalarySlip(@PathVariable Long employeeId, HttpServletResponse response)
            throws IOException, DocumentException {
        SalarySlip salarySlip = salarySlipService.getSalarySlipByEmployeeId(employeeId);
        SalarySlipExporter.exportSalarySlip(response, salarySlip);
    }

    @PostMapping("/update")
    public String updateSalarySlip(@RequestParam Long employeeId, @RequestParam double newBaseSalary,
            RedirectAttributes redirectAttributes) {
        salarySlipService.updateSalarySlip(employeeId, newBaseSalary);
        redirectAttributes.addFlashAttribute("success", "Salary slip updated successfully!");
        return "redirect:/admin/salary-slips";
    }
}