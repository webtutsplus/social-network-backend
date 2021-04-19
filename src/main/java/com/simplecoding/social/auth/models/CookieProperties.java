package com.simplecoding.social.auth.models;

public class CookieProperties {
	String domain;
	String path;
	boolean httpOnly;
	boolean secure;
	int maxAgeInMinutes;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isHttpOnly() {
		return httpOnly;
	}

	public void setHttpOnly(boolean httpOnly) {
		this.httpOnly = httpOnly;
	}

	public boolean isSecure() {
		return secure;
	}

	public void setSecure(boolean secure) {
		this.secure = secure;
	}

	public int getMaxAgeInMinutes() {
		return maxAgeInMinutes;
	}

	public void setMaxAgeInMinutes(int maxAgeInMinutes) {
		this.maxAgeInMinutes = maxAgeInMinutes;
	}
}
