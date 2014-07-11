package com.innovez.sample.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.innovez.sample.domain.entity.Project;
import com.innovez.sample.domain.entity.Project.Status;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
	List<Project> findAllByStatus(Status status);
	Page<Project> findAllByStatus(Status status, Pageable pageable);
}