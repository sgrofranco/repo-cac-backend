package ar.com.codoacodo.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ar.com.codoacodo.domain.User;

public class UserSecurity implements UserDetails{
	
	private User user;
	
	public UserSecurity(User userFromDB) {
		this.user = userFromDB;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return this.user.getRoles()
			.stream()
			.map(r -> r.getRol())
			.map(SimpleGrantedAuthority::new)
			.toList();
		
	}

	@Override
	public String getPassword() {
					
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {		
		
		return this.user.getUsername();
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
		return true;
	}

}
