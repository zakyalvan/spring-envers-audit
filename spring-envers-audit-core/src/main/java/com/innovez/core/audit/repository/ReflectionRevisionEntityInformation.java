package com.innovez.core.audit.repository;

import org.hibernate.envers.RevisionNumber;
import org.springframework.data.repository.history.support.RevisionEntityInformation;
import org.springframework.data.util.AnnotationDetectionFieldCallback;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

public class ReflectionRevisionEntityInformation implements RevisionEntityInformation {
	private final Class<?> revisionEntityClass;
	private final Class<?> revisionNumberType;

	public ReflectionRevisionEntityInformation(Class<?> revisionEntityClass) {
		Assert.notNull(revisionEntityClass, "Revision entity class or type constructor parameter should not be null");

		AnnotationDetectionFieldCallback fieldCallback = new AnnotationDetectionFieldCallback(RevisionNumber.class);
		ReflectionUtils.doWithFields(revisionEntityClass, fieldCallback);

		this.revisionNumberType = fieldCallback.getType();
		this.revisionEntityClass = revisionEntityClass;
	}

	public Class<?> getRevisionNumberType() {
		return revisionNumberType;
	}

	public boolean isDefaultRevisionEntity() {
		return false;
	}

	public Class<?> getRevisionEntityClass() {
		return revisionEntityClass;
	}
}
