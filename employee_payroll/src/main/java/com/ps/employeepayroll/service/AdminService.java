package com.ps.employeepayroll.service;

import com.ps.employeepayroll.model.Admin;
import com.ps.employeepayroll.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public boolean adminExists() {
        return adminRepository.count() > 0; // Returns true if at least one admin exists
    }

    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public Admin getAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

}
