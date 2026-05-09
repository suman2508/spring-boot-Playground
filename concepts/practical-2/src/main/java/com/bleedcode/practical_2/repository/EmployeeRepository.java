package com.bleedcode.practical_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bleedcode.practical_2.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
