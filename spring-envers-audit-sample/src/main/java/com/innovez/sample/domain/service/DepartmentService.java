package com.innovez.sample.domain.service;

import java.util.Collection;

import com.innovez.sample.domain.entity.Department;

public interface DepartmentService {
	Department createDepartment(Department department);
	Collection<Department> getAllDepartments();
	Department getDepartment(Short departmentId);
	boolean isRegisteredDepartment(Short departmentId);
	void updateDepartment(Short departmentId, Department department);
	void deleteDepartment(Short departmentId);
}
