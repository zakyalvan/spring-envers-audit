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

@Entity
@Audited
@RevisionEntity()
@Table(name="innvz_revision_info")
@SuppressWarnings("serial")
public class RevisionInfoEntity implements Serializable {
	@Id
	@RevisionNumber
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="revision")
	private Long number;
	
	@NotNull
	@RevisionTimestamp
	@Column(name="timestamp")
	private Long timestamp;
	
	@Column(name="principal")
	private String principal;
	
	public Long getNumber() {
		return number;
	}
	@Transient
	public Date getTimestamp() {
		return new Date(timestamp);
	}
	public Principal getPrincipal() {
		return null;
	}
}
