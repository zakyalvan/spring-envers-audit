package com.innovez.sample.domain.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.innovez.sample.domain.entity.Project;
import com.innovez.sample.domain.entity.Project.Status;
import com.innovez.sample.domain.repository.ProjectRepository;

@Service
@Transactional(readOnly=true)
public class JpaRepoBackedProjectService implements ProjectService {
	@Autowired
	private ProjectRepository projectRepository;
	
	@Override
	public Collection<Project> getAllProjects() {
		return projectRepository.findAll();
	}

	@Override
	public Collection<Project> getAllProjectsWithStatus(Status status) {
		Assert.notNull(status);
		return projectRepository.findAllByStatus(status);
	}

	@Override
	public Project getProject(Integer projectId) {
		Assert.notNull(projectId);
		return projectRepository.findOne(projectId);
	}

	@Override
	public Project createProject(String name, String description) {
		Project project = new Project(name, description);
		return projectRepository.save(project);
	}

	@Override
	public boolean isRegisteredProject(Integer projectId) {
		Assert.notNull(projectId);
		return projectRepository.exists(projectId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void startProject(Integer projectId) {
		Assert.notNull(projectId);
		Assert.isTrue(isRegisteredProject(projectId));
		
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateProject(Integer projectId, Project project) {
		projectRepository.save(project);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteProject(Integer projectId) {
		Assert.notNull(projectId);
		Assert.isTrue(isRegisteredProject(projectId));
		projectRepository.delete(projectId);
	}
}
