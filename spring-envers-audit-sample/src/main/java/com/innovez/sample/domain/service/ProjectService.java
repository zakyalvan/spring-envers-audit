package com.innovez.sample.domain.service;

import java.util.Collection;

import com.innovez.sample.domain.entity.Project;

public interface ProjectService {
	Collection<Project> getAllProjects();
	Project getProject(Integer projectId);
	Project createProject(String name, String description);
	boolean isRegisteredProject(Integer projectId);
	void updateProject(Integer projectId, Project project);
	void deleteProject(Integer projectId);
}
