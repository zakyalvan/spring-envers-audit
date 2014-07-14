package com.innovez.core.audit.entity;

import java.io.Serializable;
import java.security.Principal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import com.innovez.core.audit.listener.RevisionInfoListener;

@Entity
@Audited
@RevisionEntity(RevisionInfoListener.class)
@Table(name="innvz_revision_info")
@SuppressWarnings("serial")
public class RevisionInfoEntity implements Serializable {
	@Id
	@RevisionNumber
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="revision")
	private Long revision;
	
	@NotNull
	@RevisionTimestamp
	@Column(name="timestamp")
	private Long timestamp;
	
	@Column(name="principal")
	private String principal;
	
	public Long getRevision() {
		return revision;
	}
	@Transient
	public Date getTimestamp() {
		return new Date(timestamp);
	}
	public Principal getPrincipal() {
		return null;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((revision == null) ? 0 : revision.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RevisionInfoEntity other = (RevisionInfoEntity) obj;
		if (revision == null) {
			if (other.revision != null)
				return false;
		} else if (!revision.equals(other.revision))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "RevisionInfoEntity [revision=" + revision + ", timestamp="
				+ timestamp + ", principal=" + principal + "]";
	}
}