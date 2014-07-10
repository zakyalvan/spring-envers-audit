package com.innovez.core.audit.repository;

import org.hibernate.envers.DefaultRevisionEntity;
import org.joda.time.DateTime;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.util.Assert;

public class DefaultRevisionMetadata implements RevisionMetadata<Integer> {
	private final DefaultRevisionEntity entity;

	public DefaultRevisionMetadata(DefaultRevisionEntity entity) {

		Assert.notNull(entity);
		this.entity = entity;
	}

	public Integer getRevisionNumber() {
		return entity.getId();
	}

	public DateTime getRevisionDate() {
		return new DateTime(entity.getTimestamp());
	}

	@SuppressWarnings("unchecked")
	public <T> T getDelegate() {
		return (T) entity;
	}
}