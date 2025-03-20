package com.ps.employeepayroll.repository;

import com.ps.employeepayroll.model.Admin;
import com.ps.employeepayroll.model.Employee;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Optional<Employee> findByEmail(String email);

    List<Employee> findByAdmin(Admin admin);

    Optional<Employee> findByEmployeeId(String employeeId);
}