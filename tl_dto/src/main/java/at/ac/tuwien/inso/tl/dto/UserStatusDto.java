package at.ac.tuwien.inso.tl.dto;

import java.util.ArrayList;
import java.util.List;

public class UserStatusDto {

	private UserEvent event;
	
	private String username;
	
	private String firstname;

	private String lastname;
	
	private boolean isLocked;

	private boolean anonymous = true;
	
	private List<String> roles = new ArrayList<String>();

	public UserEvent getEvent() {
		return event;
	}

	public void setEvent(UserEvent event) {
		this.event = event;
	}

	public String getUsername() {
		return username;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}
	
	public boolean getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setFirstName(String firstname) {
		this.firstname = firstname;
	}
	
	public void setLastName(String lastname) {
		this.lastname = lastname;
	}
	
	public boolean isAnonymous() {
		return anonymous;
	}

	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}
