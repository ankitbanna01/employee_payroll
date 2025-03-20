package com.ps.employeepayroll.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ps.employeepayroll.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}