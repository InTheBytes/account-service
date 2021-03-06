package com.inthebytes.accountservice.auth;

import java.util.ArrayList;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.inthebytes.stacklunch.data.user.User;


public class LoginPrincipal implements UserDetails {
	
	private static final long serialVersionUID = -1610064484091786350L;

	private User user;

	public LoginPrincipal(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		String r = "ROLE_" + user.getRole().getName().toUpperCase();
		GrantedAuthority auth = new SimpleGrantedAuthority(r);
		authorities.add(auth);
		return authorities;
	}

	@Override
	public String getPassword() {
		if (user == null)
			return null;
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.getActive();
	}

}
