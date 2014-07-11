package com.innovez.core.audit.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.hibernate.envers.DefaultRevisionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.history.support.RevisionEntityInformation;

/**
 * Factory bean for revision aware jpa repository implementation.
 * 
 * @author zakyalvan
 */
public class RevisionAwareJpaRepositoryFactoryBean extends JpaRepositoryFactoryBean<RevisionAwareJpaRepository<Object, Serializable, Long>, Object, Serializable> {
	private Logger logger = LoggerFactory.getLogger(RevisionAwareJpaRepositoryFactoryBean.class);
	
	private Class<?> revisionEntityClass;

	public void setRevisionEntityClass(Class<?> revisionEntityClass) {
		this.revisionEntityClass = revisionEntityClass;
	}

	@Override
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
		logger.debug("Create revision aware jpa repository factory object, in which actually repository will be created");
		return new RevisionAwareJpaRepositoryFactory(entityManager, revisionEntityClass);
	}

	/**
	 * Factory class for {@link RevisionAwareJpaRepository} implementation.
	 * 
	 * @author zakyalvan
	 */
	private static class RevisionAwareJpaRepositoryFactory extends JpaRepositoryFactory {
		private final RevisionEntityInformation revisionEntityInformation;

		public RevisionAwareJpaRepositoryFactory(EntityManager entityManager, Class<?> revisionEntityClass) {
			super(entityManager);
			revisionEntityClass = revisionEntityClass == null ? DefaultRevisionEntity.class : revisionEntityClass;
			this.revisionEntityInformation = DefaultRevisionEntity.class.equals(revisionEntityClass) ? new DefaultRevisionEntityInformation()
					: new ReflectionRevisionEntityInformation(revisionEntityClass);
		}
		
		@Override
		@SuppressWarnings({ "unchecked", "rawtypes" })
		protected <T, ID extends Serializable> SimpleJpaRepository<?, ?> getTargetRepository(RepositoryMetadata metadata,
				EntityManager entityManager) {

			JpaEntityInformation<T, Serializable> entityInformation = (JpaEntityInformation<T, Serializable>) getEntityInformation(metadata
					.getDomainType());
			return new DefaultRevisionAwareJpaRepository(entityInformation, revisionEntityInformation, entityManager);
		}

		@Override
		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			return DefaultRevisionAwareJpaRepository.class;
		}

		@Override
		public <T> T getRepository(Class<T> repositoryInterface, Object customImplementation) {
			if (RevisionRepository.class.isAssignableFrom(repositoryInterface)) {
				Class<?>[] typeArguments = GenericTypeResolver.resolveTypeArguments(repositoryInterface, RevisionRepository.class);
				Class<?> revisionNumberType = typeArguments[2];

				if (!revisionEntityInformation.getRevisionNumberType().equals(revisionNumberType)) {
					throw new IllegalStateException(String.format(
							"Configured a revision entity type of %s with a revision type of %s "
									+ "but the repository interface is typed to a revision type of %s!", repositoryInterface,
							revisionEntityInformation.getRevisionNumberType(), revisionNumberType));
				}
			}

			return super.getRepository(repositoryInterface, customImplementation);
		}
	}
}
