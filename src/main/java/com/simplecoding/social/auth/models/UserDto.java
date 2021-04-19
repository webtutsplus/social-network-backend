package com.simplecoding.social.auth.models;


import java.io.Serializable;

public class UserDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4408418647685225829L;
	private String uid;
	private String name;
	private String email;
	private boolean isEmailVerified;
	private String issuer;
	private String picture;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEmailVerified() {
		return isEmailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		isEmailVerified = emailVerified;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
}
