package com.innovez.core.audit.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.history.RevisionRepository;

@NoRepositoryBean
public interface RevisionAwareJpaRepository<T, ID extends Serializable, N extends Number & Comparable<N>> extends RevisionRepository<T, ID, N>, JpaRepository<T, ID> {

}
