package com.innovez.core.audit.repository;

import org.joda.time.DateTime;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.util.Assert;

import com.innovez.core.audit.entity.RevisionInfoEntity;

/**
 * Default implementation of revisio-metadata type.
 * 
 * @author zakyalvan
 */
public class DefaultRevisionMetadata implements RevisionMetadata<Long> {
	private final RevisionInfoEntity revisionInfoEntity;

	public DefaultRevisionMetadata(RevisionInfoEntity revisionInfoEntity) {
		Assert.notNull(revisionInfoEntity);
		this.revisionInfoEntity = revisionInfoEntity;
	}

	public Long getRevisionNumber() {
		return revisionInfoEntity.getNumber();
	}

	public DateTime getRevisionDate() {
		return new DateTime(revisionInfoEntity.getTimestamp());
	}

	@SuppressWarnings("unchecked")
	public <T> T getDelegate() {
		return (T) revisionInfoEntity;
	}
}