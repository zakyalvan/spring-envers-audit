package com.innovez.core.audit.repository;

import org.springframework.data.repository.history.support.RevisionEntityInformation;

import com.innovez.core.audit.entity.RevisionInfoEntity;

/**
 * Represent summary information of {@link RevisionInfoEntity}.
 * 
 * @author zakyalvan
 */
class DefaultRevisionEntityInformation implements RevisionEntityInformation {
	public Class<?> getRevisionNumberType() {
		return Long.class;
	}
	public boolean isDefaultRevisionEntity() {
		return true;
	}
	public Class<?> getRevisionEntityClass() {
		return RevisionInfoEntity.class;
	}
}
