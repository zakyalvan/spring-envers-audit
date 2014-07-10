package com.innovez.sample.domain.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.innovez.sample.domain.entity.Department;
import com.innovez.sample.domain.repository.DepartmentRepository;

@Service
@Transactional(readOnly=true)
public class JpaRepoBackedDepartmentService implements DepartmentService {
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Department createDepartment(Department department) {
		return null;
	}

	@Override
	public Collection<Department> getAllDepartments() {
		return null;
	}

	@Override
	public Department getDepartment(Short departmentId) {
		return null;
	}

	@Override
	public boolean isRegisteredDepartment(Short departmentId) {
		return false;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateDepartment(Department department) {
		
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteDepartment(Short departmentId) {
		
	}

}
