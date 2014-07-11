package com.innovez.core.security.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="innvz_user")
@SuppressWarnings("serial")
public class User implements Serializable {
	@Column(name="username")
	private String username;
}
