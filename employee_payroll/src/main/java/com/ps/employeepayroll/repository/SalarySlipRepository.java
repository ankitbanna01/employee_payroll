package com.ps.employeepayroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ps.employeepayroll.model.SalarySlip;

import java.util.Optional;

@Repository
public interface SalarySlipRepository extends JpaRepository<SalarySlip, Long> {
    Optional<SalarySlip> findByEmployeeId(Long employeeId);
}
