package com.innovez.sample.domain.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.innovez.sample.domain.entity.Employee;
import com.innovez.sample.domain.repository.EmployeeRepository;

@Service
@Transactional(readOnly=true)
public class JpaRepoBackedEmployeeService implements EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Employee registerEmployee(Employee employee) {
		Assert.notNull(employee);
		return employeeRepository.save(employee);
	}

	@Override
	public boolean isRegisteredEmployee(Integer employeeId) {
		return employeeRepository.exists(employeeId);
	}

	@Override
	public Collection<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee getEmployee(Integer employeeId) {
		Assert.notNull(employeeId);
		Assert.isTrue(isRegisteredEmployee(employeeId));
		return employeeRepository.findOne(employeeId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateEmployee(Employee employee) {
		Assert.notNull(employee);
		employeeRepository.save(employee);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteEmployee(Integer employeeId) {
		Assert.notNull(employeeId);
		Assert.isTrue(isRegisteredEmployee(employeeId));
		employeeRepository.delete(employeeId);
	}
}
