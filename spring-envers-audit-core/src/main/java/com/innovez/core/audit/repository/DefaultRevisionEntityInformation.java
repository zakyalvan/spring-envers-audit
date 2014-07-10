package com.innovez.core.audit.repository;

import org.hibernate.envers.DefaultRevisionEntity;
import org.springframework.data.repository.history.support.RevisionEntityInformation;

class DefaultRevisionEntityInformation implements RevisionEntityInformation {
	public Class<?> getRevisionNumberType() {
		return Integer.class;
	}

	public boolean isDefaultRevisionEntity() {
		return true;
	}

	public Class<?> getRevisionEntityClass() {
		return DefaultRevisionEntity.class;
	}
}
