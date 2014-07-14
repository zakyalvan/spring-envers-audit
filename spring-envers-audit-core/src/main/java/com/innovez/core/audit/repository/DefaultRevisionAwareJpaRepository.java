package com.innovez.core.audit.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.AnnotationRevisionMetadata;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.data.history.Revisions;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.history.support.RevisionEntityInformation;
import org.springframework.util.Assert;

import com.innovez.core.audit.entity.RevisionInfoEntity;

/**
 * Default implementation of {@link RevisionAwareJpaRepository}.
 * 
 * @author zakyalvan
 *
 * @param <T>
 * @param <ID>
 * @param <N>
 */
public class DefaultRevisionAwareJpaRepository<T, ID extends Serializable, N extends Number & Comparable<N>> extends SimpleJpaRepository<T, ID> implements RevisionRepository<T, ID, N> {
	private final EntityInformation<T, ?> entityInformation;
	private final RevisionEntityInformation revisionEntityInformation;
	private final EntityManager entityManager;

	public DefaultRevisionAwareJpaRepository(JpaEntityInformation<T, ?> entityInformation, RevisionEntityInformation revisionEntityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);

		Assert.notNull(revisionEntityInformation);

		this.entityInformation = entityInformation;
		this.revisionEntityInformation = revisionEntityInformation;
		this.entityManager = entityManager;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Revision<N, T> findLastChangeRevision(ID id) {
		Class<T> type = entityInformation.getJavaType();
		AuditReader reader = AuditReaderFactory.get(entityManager);

		List<Number> revisions = reader.getRevisions(type, id);

		if (revisions.isEmpty()) {
			return null;
		}

		N latestRevision = (N) revisions.get(revisions.size() - 1);

		Class<?> revisionEntityClass = revisionEntityInformation.getRevisionEntityClass();

		Object revisionEntity = reader.findRevision(revisionEntityClass, latestRevision);
		RevisionMetadata<N> metadata = (RevisionMetadata<N>) getRevisionMetadata(revisionEntity);
		return new Revision<N, T>(metadata, reader.find(type, id, latestRevision));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Revisions<N, T> findRevisions(ID id) {
		Class<T> type = entityInformation.getJavaType();
		AuditReader reader = AuditReaderFactory.get(entityManager);
		List<? extends Number> revisionNumbers = reader.getRevisions(type, id);

		return revisionNumbers.isEmpty() ? new Revisions<N, T>(Collections.EMPTY_LIST) : getEntitiesForRevisions((List<N>) revisionNumbers, id, reader);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Page<Revision<N, T>> findRevisions(ID id, Pageable pageable) {
		Class<T> type = entityInformation.getJavaType();
		AuditReader reader = AuditReaderFactory.get(entityManager);
		List<Number> revisionNumbers = reader.getRevisions(type, id);

		if (pageable.getOffset() > revisionNumbers.size()) {
			return new PageImpl<Revision<N, T>>(Collections.<Revision<N, T>> emptyList(), pageable, 0);
		}

		int upperBound = pageable.getOffset() + pageable.getPageSize();
		upperBound = upperBound > revisionNumbers.size() ? revisionNumbers.size() : upperBound;

		List<? extends Number> subList = revisionNumbers.subList(pageable.getOffset(), upperBound);
		Revisions<N, T> revisions = getEntitiesForRevisions((List<N>) subList, id, reader);

		return new PageImpl<Revision<N, T>>(revisions.getContent(), pageable, revisionNumbers.size());
	}

	@SuppressWarnings("unchecked")
	private Revisions<N, T> getEntitiesForRevisions(List<N> revisionNumbers, ID id, AuditReader reader) {
		Class<T> type = entityInformation.getJavaType();
		Map<N, T> revisions = new HashMap<N, T>(revisionNumbers.size());

		Class<?> revisionEntityClass = revisionEntityInformation.getRevisionEntityClass();
		Map<Number, Object> revisionEntities = (Map<Number, Object>) reader.findRevisions(revisionEntityClass,
				new HashSet<Number>(revisionNumbers));

		for (Number number : revisionNumbers) {
			revisions.put((N) number, reader.find(type, id, number));
		}

		return new Revisions<N, T>(toRevisions(revisions, revisionEntities));
	}

	@SuppressWarnings("unchecked")
	private List<Revision<N, T>> toRevisions(Map<N, T> source, Map<Number, Object> revisionEntities) {
		List<Revision<N, T>> result = new ArrayList<Revision<N, T>>();
		for (Entry<N, T> revision : source.entrySet()) {
			N revisionNumber = revision.getKey();
			T entity = revision.getValue();
			RevisionMetadata<N> metadata = (RevisionMetadata<N>) getRevisionMetadata(revisionEntities.get(revisionNumber));
			result.add(new Revision<N, T>(metadata, entity));
		}

		Collections.sort(result);
		return Collections.unmodifiableList(result);
	}

	private RevisionMetadata<?> getRevisionMetadata(Object object) {
		if (object instanceof DefaultRevisionEntity) {
			return new DefaultRevisionMetadata((RevisionInfoEntity) object);
		} else {
			return new AnnotationRevisionMetadata<N>(object, RevisionNumber.class, RevisionTimestamp.class);
		}
	}
}
