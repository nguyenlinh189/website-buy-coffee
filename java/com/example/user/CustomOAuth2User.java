package com.example.user;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User {
	private OAuth2User oaAuth2User;
	
	public CustomOAuth2User(OAuth2User oaAuth2User) {
		super();
		this.oaAuth2User = oaAuth2User;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return oaAuth2User.getAttributes();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return oaAuth2User.getAuthorities();
	}

	@Override
	public String getName() {
		return oaAuth2User.getAttribute("name");
	}
	public String getEmail() {
		return oaAuth2User.<String>getAttribute("email");
	}
}
