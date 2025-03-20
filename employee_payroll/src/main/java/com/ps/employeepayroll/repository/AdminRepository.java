package com.ps.employeepayroll.repository;

import com.ps.employeepayroll.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, String> {
    // Find an admin by email
    Admin findByEmail(String email);
}