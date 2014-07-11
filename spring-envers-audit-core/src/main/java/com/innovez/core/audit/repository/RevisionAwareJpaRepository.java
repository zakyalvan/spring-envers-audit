package com.innovez.core.audit.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.history.RevisionRepository;

/**
 * Interface to be implemented by repository contract to get revision capability
 * of any jpa repository. Remember, please use Hibernate Envers specific
 * annotation to enable audit feature.
 * 
 * @author zakyalvan
 *
 * @param <T>
 * @param <ID>
 * @param <N>
 */
@NoRepositoryBean
public interface RevisionAwareJpaRepository<T, ID extends Serializable, N extends Number & Comparable<N>>
		extends RevisionRepository<T, ID, N>, JpaRepository<T, ID> {
}
