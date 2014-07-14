package com.innovez.sample.domain.service;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.innovez.sample.domain.entity.Employee;

public interface EmployeeService {
	Employee registerEmployee(Employee employee);
	boolean isRegisteredEmployee(Integer employeeId);
	Collection<Employee> getEmployees();
	Page<Employee> getEmployees(Pageable pageable);
	Employee getEmployee(Integer employeeId);
	void updateEmployee(Employee employee);
	void deleteEmployee(Integer employeeId);
}
