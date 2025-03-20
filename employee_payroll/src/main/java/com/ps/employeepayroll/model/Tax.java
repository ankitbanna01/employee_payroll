package com.ps.employeepayroll.model;

import jakarta.persistence.*;

@Entity
@Table(name = "taxes")
public class Tax {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double taxPercentage;

    public Tax() {
    }

    public Tax(double taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public double calculateTax(double salary) {
        return salary * (this.taxPercentage / 100);
    }

    // Getters and Setters
}