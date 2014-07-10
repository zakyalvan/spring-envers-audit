package com.innovez.sample.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innovez.sample.domain.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
