package com.innovez.sample.domain.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
		Assert.notNull(department);
		return departmentRepository.save(department);
	}

	@Override
	public Collection<Department> getAllDepartments() {
		return departmentRepository.findAll();
	}

	@Override
	public Department getDepartment(Short departmentId) {
		Assert.notNull(departmentId);
		Assert.isTrue(isRegisteredDepartment(departmentId));
		return departmentRepository.findOne(departmentId);
	}

	@Override
	public boolean isRegisteredDepartment(Short departmentId) {
		Assert.notNull(departmentId);
		return departmentRepository.exists(departmentId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateDepartment(Short departmentId, Department department) {
		Assert.notNull(departmentId);
		Assert.notNull(department);
		Assert.isTrue(isRegisteredDepartment(departmentId));
		departmentRepository.delete(department);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteDepartment(Short departmentId) {
		Assert.notNull(departmentId);
		Assert.isTrue(isRegisteredDepartment(departmentId));
		departmentRepository.delete(departmentId);
	}
}
