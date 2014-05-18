package at.ac.tuwien.inso.tl.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EmployeeDto {
	@NotNull
	@Size(max=50)	
	private String firstname;		
	
	@NotNull	
	private Boolean isadmin;	
	
	@NotNull
	@Size(max=50)	
	private String lastname;
	
	@NotNull
	@Size(min=512, max=512)	
	private String passwordHash;
	
	@NotNull
	@Size(max=50)	
	private String username;
	
	@NotNull
	@Size(min=0, max=5)
	private Integer wrongPasswordCounter;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public Boolean getIsadmin() {
		return isadmin;
	}

	public void setIsadmin(Boolean isadmin) {
		this.isadmin = isadmin;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getWrongPasswordCounter() {
		return wrongPasswordCounter;
	}

	public void setWrongPasswordCounter(Integer wrongPasswordCounter) {
		this.wrongPasswordCounter = wrongPasswordCounter;
	}	
	
	@Override
	public String toString() {
		return "EmployeeDto [firstname=" + firstname + ", isadmin=" + isadmin + ", lastname=" + lastname			
				+ ", passwordHash=" + passwordHash + ", username=" + username+ ", wrongPasswordCounter=" + wrongPasswordCounter + "]";
	}
}
