package com.innovez.core.audit.entity;

import java.io.Serializable;
import java.security.Principal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
public class RevisionInformation implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="rev_number")
	private Long number;
	
	@NotNull
	@Column(name="rev_timestamp")
	private Long timestamp;
	
	@Column(name="rev_principal")
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
