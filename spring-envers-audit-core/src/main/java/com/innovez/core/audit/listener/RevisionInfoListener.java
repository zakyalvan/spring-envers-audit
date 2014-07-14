package com.innovez.core.audit.listener;

import org.hibernate.envers.RevisionListener;

import com.innovez.core.audit.entity.RevisionInfoEntity;

/**
 * Default implementation of {@link RevisionListener}. In this class, we only add principal information,
 * who made revisions on entity, for security aspect reason.
 * 
 * @author zakyalvan
 */
public class RevisionInfoListener implements RevisionListener {
	@Override
	public void newRevision(Object revisionEntity) {
		RevisionInfoEntity revisionInfoEntity = (RevisionInfoEntity) revisionEntity;
	}
}
