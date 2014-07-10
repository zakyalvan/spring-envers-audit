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

import com.innovez.sample.domain.entity.Project;
import com.innovez.sample.domain.service.ProjectService;

@Controller
@RequestMapping(value="/projects**")
public class ProjectController {
	private Logger logger = LoggerFactory.getLogger(ProjectController.class);
	
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value={"", "/"}, method=RequestMethod.GET)
	public HttpEntity<Collection<Project>> list() {
		logger.debug("List all registered projects");
		return new ResponseEntity<Collection<Project>>(projectService.getAllProjects(), HttpStatus.OK);
	}
	@RequestMapping(value="/{projectId}", method=RequestMethod.GET)
	public HttpEntity<Project> details(@PathVariable Integer projectId) {
		logger.debug(String.format("Show details of project with id %d", projectId));
		if(!projectService.isRegisteredProject(projectId)) {
			return new ResponseEntity<Project>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Project>(projectService.getProject(projectId), HttpStatus.OK);
	}
	@RequestMapping(value={"", "/"}, method=RequestMethod.POST)
	public HttpEntity<Project> create(@Valid @RequestBody Project project, BindingResult bindingResult) {
		logger.debug("Create new project");
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<Project>(HttpStatus.BAD_REQUEST);
		}
		Project newProject = projectService.createProject(project.getName(), project.getDescription());
		return new ResponseEntity<Project>(newProject, HttpStatus.CREATED);
	}
	@RequestMapping(value="/{projectId}", method=RequestMethod.PUT)
	public HttpEntity<Project> update(@PathVariable Integer projectId, @Valid @RequestBody Project project, BindingResult bindingResult) {
		logger.debug(String.format("Update project with id %d", projectId));
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<Project>(HttpStatus.BAD_REQUEST);
		}
		projectService.updateProject(projectId, project);
		return new ResponseEntity<Project>(HttpStatus.OK);
	}
	@RequestMapping(value="/{projectId}", method=RequestMethod.DELETE)
	public HttpEntity<Project> delete(@PathVariable Integer projectId, @Valid @RequestBody Project project, BindingResult bindingResult) {
		logger.debug(String.format("Delete project with id %d", projectId));
		projectService.deleteProject(projectId);
		return new ResponseEntity<Project>(HttpStatus.OK);
	}
}
