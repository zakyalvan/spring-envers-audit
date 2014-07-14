package com.innovez.core.audit.repository;

import org.joda.time.DateTime;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.util.Assert;

import com.innovez.core.audit.entity.RevisionInfoEntity;

/**
 * Default implementation of class which hold meta information of revision.
 * 
 * @author zakyalvan
 */
public class DefaultRevisionMetadata implements RevisionMetadata<Long> {
	private final RevisionInfoEntity revisionInfoEntity;

	public DefaultRevisionMetadata(RevisionInfoEntity revisionInfoEntity) {
		Assert.notNull(revisionInfoEntity, "Revision info entity should not be null");
		this.revisionInfoEntity = revisionInfoEntity;
	}

	public Long getRevisionNumber() {
		return revisionInfoEntity.getRevision();
	}

	public DateTime getRevisionDate() {
		return new DateTime(revisionInfoEntity.getTimestamp());
	}

	@SuppressWarnings("unchecked")
	public <T> T getDelegate() {
		return (T) revisionInfoEntity;
	}
}