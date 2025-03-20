package com.ps.employeepayroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ps.employeepayroll.model.SalarySlip;
import java.util.Optional;

public interface SalarySlipRepository extends JpaRepository<SalarySlip, Long> {
    Optional<SalarySlip> findByEmployee_EmployeeId(String employeeId);
}
