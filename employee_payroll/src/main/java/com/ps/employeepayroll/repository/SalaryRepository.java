package com.ps.employeepayroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ps.employeepayroll.model.Salary;

public interface SalaryRepository extends JpaRepository<Salary, Long> {

}
