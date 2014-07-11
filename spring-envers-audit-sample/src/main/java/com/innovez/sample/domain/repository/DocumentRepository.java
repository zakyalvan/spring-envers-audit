package com.innovez.sample.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innovez.sample.domain.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
	
}
