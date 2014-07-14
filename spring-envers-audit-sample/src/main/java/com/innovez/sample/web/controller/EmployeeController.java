package com.innovez.sample.web.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.innovez.sample.domain.entity.Employee;
import com.innovez.sample.domain.service.EmployeeService;

@Controller
@RequestMapping(value="/api/employees**")
public class EmployeeController {
	private Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(value={"", "/"}, method=RequestMethod.GET)
	public HttpEntity<Page<Employee>> list(Pageable pageable) {
		logger.debug("Show list of all registered employees");
		return new ResponseEntity<Page<Employee>>(employeeService.getEmployees(pageable), HttpStatus.OK);
	}
	@RequestMapping(value="/{employeeId}", method=RequestMethod.GET)
	public HttpEntity<Employee> details(@PathVariable Integer employeeId) {
		if(!employeeService.isRegisteredEmployee(employeeId)) {
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Employee>(employeeService.getEmployee(employeeId), HttpStatus.OK);
	}
	@RequestMapping(value={"", "/"}, method=RequestMethod.POST)
	public HttpEntity<Employee> create(@Valid @RequestBody Employee employee, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<Employee>(HttpStatus.BAD_REQUEST);
		}
		Employee newEmployee = employeeService.registerEmployee(employee);
		return new ResponseEntity<Employee>(newEmployee, HttpStatus.CREATED);
	}
	@RequestMapping(value="/{employeeId}", method=RequestMethod.PUT)
	public HttpEntity<Employee> update(@PathVariable Integer employeeId, @Valid @RequestBody Employee employee, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<Employee>(HttpStatus.BAD_REQUEST);
		}
		employeeService.updateEmployee(employee);
		return new ResponseEntity<Employee>(HttpStatus.OK);
	}
	@RequestMapping(value="/{employeeId}", method=RequestMethod.DELETE)
	public HttpEntity<Employee> delete(@PathVariable Integer employeeId) {
		if(!employeeService.isRegisteredEmployee(employeeId)) {
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}
		employeeService.deleteEmployee(employeeId);
		return new ResponseEntity<Employee>(HttpStatus.OK);
	}
}
