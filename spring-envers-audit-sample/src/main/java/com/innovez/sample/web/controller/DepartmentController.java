package com.innovez.sample.web.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.innovez.sample.domain.entity.Department;
import com.innovez.sample.domain.service.DepartmentService;

@Controller
@RequestMapping(value="/departments**")
public class DepartmentController {
	private Logger logger = LoggerFactory.getLogger(DepartmentController.class);
	
	@Autowired
	private DepartmentService departmentService;
	
	@RequestMapping(value={"", "/"}, method=RequestMethod.POST)
	public HttpEntity<Department> create(@Valid @RequestBody Department department, BindingResult bindingResult) {
		logger.debug("Create new department data");
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<Department>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Department>(departmentService.createDepartment(department), HttpStatus.CREATED);
	}
	@RequestMapping(value={"", "/"}, method=RequestMethod.GET)
	public HttpEntity<Collection<Department>> list() {
		logger.debug("List all registered departments");
		return new ResponseEntity<Collection<Department>>(departmentService.getAllDepartments(), HttpStatus.OK);
	}
	@RequestMapping(value="/{departmentId}", method=RequestMethod.GET)
	public HttpEntity<Department> details(@PathVariable Short departmentId) {
		logger.debug(String.format("Show details of registered department with id : %d", departmentId));
		if(!departmentService.isRegisteredDepartment(departmentId)) {
			return new ResponseEntity<Department>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Department>(departmentService.getDepartment(departmentId), HttpStatus.OK);
	}
	@RequestMapping(value="/{departmentId}", method=RequestMethod.PUT)
	public HttpEntity<Department> update(@PathVariable Short departmentId, @Valid @RequestBody Department department, BindingResult bindingResult) {
		logger.debug(String.format("Update details of department with id : %d", departmentId));
		if(!departmentService.isRegisteredDepartment(departmentId)) {
			return new ResponseEntity<Department>(HttpStatus.NOT_FOUND);
		}
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<Department>(HttpStatus.BAD_REQUEST);
		}
		departmentService.updateDepartment(departmentId, department);
		return new ResponseEntity<Department>(HttpStatus.OK);
	}
	@RequestMapping(value="/{departmentId}", method=RequestMethod.DELETE)
	public HttpEntity<Department> delete(@PathVariable Short departmentId) {
		logger.debug(String.format("Delete department data with id : %d", departmentId));
		if(!departmentService.isRegisteredDepartment(departmentId)) {
			return new ResponseEntity<Department>(HttpStatus.NOT_FOUND);
		}
		departmentService.deleteDepartment(departmentId);
		return new ResponseEntity<Department>(HttpStatus.OK);
	}
}
