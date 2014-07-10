package com.innovez.sample.domain.service;

import java.util.Collection;

import com.innovez.sample.domain.entity.Employee;

public interface EmployeeService {
	Employee registerEmployee(Employee employee);
	boolean isRegisteredEmployee(Integer employeeId);
	Collection<Employee> getAllEmployees();
	Employee getEmployee(Integer employeeId);
	void updateEmployee(Employee employee);
	void deleteEmployee(Integer employeeId);
}
