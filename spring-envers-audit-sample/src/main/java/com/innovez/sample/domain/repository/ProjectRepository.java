package com.innovez.sample.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innovez.sample.domain.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

}
