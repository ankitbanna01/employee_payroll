package com.ps.employeepayroll.repository;

import com.ps.employeepayroll.model.Admin;
import com.ps.employeepayroll.model.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
    // Find an admin by email
    Admin findByEmail(String email);

    Employee save(Employee employee);
}