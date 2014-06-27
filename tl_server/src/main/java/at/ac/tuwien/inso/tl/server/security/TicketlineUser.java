package at.ac.tuwien.inso.tl.server.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class TicketlineUser implements UserDetails {
	
	private static final int MAX_WRONG_PASSWORD_ATTEMPTS = 5;
	private static final long serialVersionUID = -8343481930819178599L;

	private String username;
	
	private String password;
	
	private String firstName;
	
	private String lastName;
	
	private int wrongPasswordCounter;

	private List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		
	public TicketlineUser(String username, String password, List<GrantedAuthority> roles, String firstName, String lastName, int wrongPasswordCounter) {
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.firstName = firstName;
		this.lastName = lastName;
		this.wrongPasswordCounter = wrongPasswordCounter;
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public String getUsername() {
		return this.username;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return wrongPasswordCounter < MAX_WRONG_PASSWORD_ATTEMPTS;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public int getWrongPasswordCounter() {
		return wrongPasswordCounter;
	}

}
