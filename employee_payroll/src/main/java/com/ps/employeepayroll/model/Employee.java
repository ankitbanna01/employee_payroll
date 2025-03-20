package com.ps.employeepayroll.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
@SequenceGenerator(name = "emp_seq", sequenceName = "employee_seq", allocationSize = 1)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emp_seq")
    private Long id;

    @NotBlank(message = "Employee name is required")
    private String name;

    @NotBlank(message = "Designation is required")
    private String designation;
}
