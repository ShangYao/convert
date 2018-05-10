package com.jinanlongen.sparrow.security;

import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
public class UrlGrantedAuthority implements GrantedAuthority {

	private String permissionUrl;

	public String getPermissionUrl() {
		return permissionUrl;
	}

	public void setPermissionUrl(String permissionUrl) {
		this.permissionUrl = permissionUrl;
	}

	public UrlGrantedAuthority(String permissionUrl) {
		this.permissionUrl = permissionUrl;
	}

	@Override
	public String getAuthority() {
		return this.permissionUrl;
	}
}
