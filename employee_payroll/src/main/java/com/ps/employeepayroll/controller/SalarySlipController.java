package com.ps.employeepayroll.controller;

import com.itextpdf.text.DocumentException;
import com.ps.employeepayroll.model.SalarySlip;
import com.ps.employeepayroll.service.SalarySlipService;
import com.ps.employeepayroll.utils.SalarySlipExporter;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/user/salary-slips")
public class SalarySlipController {

    @Autowired
    private SalarySlipService salarySlipService;

    @GetMapping("/")
    public String getAllSalarySlips() {
        return "admin_dashboard";
    }

    // View all salary slips

    // View salary slip by Employee ID
    @GetMapping("/{employeeId}")
    public SalarySlip getSalarySlip(@PathVariable Long employeeId) {
        return salarySlipService.getSalarySlipByEmployeeId(employeeId);
    }

    // Export salary slip to PDF
    @GetMapping("/export/{employeeId}")
    public void exportSalarySlip(@PathVariable Long employeeId, HttpServletResponse response)
            throws IOException, DocumentException {
        SalarySlip salarySlip = salarySlipService.getSalarySlipByEmployeeId(employeeId);
        SalarySlipExporter.exportSalarySlip(response, salarySlip); // Export logic
    }
}
