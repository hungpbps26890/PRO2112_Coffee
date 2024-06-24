package com.poly.coffee.auth.oauthuser;

import lombok.Getter;

import java.util.Map;

@Getter
public abstract class OAuth2UserInfo {

	protected Map<String, Object> attributes;

	public OAuth2UserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	protected abstract String getName();

	protected abstract String getFirstname();

	protected abstract String getLastname();

	protected abstract String getEmail();

}
