package com.poly.coffee.auth.oauthuser;

import java.util.Map;

public class GoogleUserInfo extends OAuth2UserInfo {

	public GoogleUserInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	protected String getName() {
		return (String) attributes.get("name");
	}

	@Override
	protected String getFirstname() {
		return (String) attributes.get("given_name");
	}

	@Override
	protected String getLastname() {
		return (String) attributes.get("family_name");
	}

	@Override
	protected String getEmail() {
		return (String) attributes.get("email");
	}

}
