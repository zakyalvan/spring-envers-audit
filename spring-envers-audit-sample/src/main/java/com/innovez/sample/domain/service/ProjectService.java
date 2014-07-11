package com.innovez.sample.domain.service;

import java.util.Collection;

import com.innovez.sample.domain.entity.Project;
import com.innovez.sample.domain.entity.Project.Status;

public interface ProjectService {
	Collection<Project> getAllProjects();
	Collection<Project> getAllProjectsWithStatus(Status status);
	Project getProject(Integer projectId);
	Project createProject(String name, String description);
	boolean isRegisteredProject(Integer projectId);
	void startProject(Integer projectId);
	void updateProject(Integer projectId, Project project);
	void deleteProject(Integer projectId);
}
