package com.innovez.sample.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innovez.sample.domain.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Short> {

}
