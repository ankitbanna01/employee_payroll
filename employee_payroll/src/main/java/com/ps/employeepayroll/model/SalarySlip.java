package com.ps.employeepayroll.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "salary_slips")
public class SalarySlip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double basicSalary;
    private double taxAmount;
    private double netSalary;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public SalarySlip() {
    }

    public SalarySlip(double basicSalary, double taxAmount, double netSalary, Employee employee) {
        this.basicSalary = basicSalary;
        this.taxAmount = taxAmount;
        this.netSalary = netSalary;
        this.employee = employee;
    }

    public Object getGeneratedDate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGeneratedDate'");
    }

    // Getters and Setters
}
