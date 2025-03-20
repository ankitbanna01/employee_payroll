package com.ps.employeepayroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ps.employeepayroll.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
