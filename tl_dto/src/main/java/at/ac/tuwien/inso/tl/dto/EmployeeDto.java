package at.ac.tuwien.inso.tl.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class EmployeeDto {
	
	private Integer id;
	
	@NotBlank
	@Size(min=1, max=50)	
	private String firstname;		
	
	@NotNull	
	private Boolean isadmin;	
	
	@NotNull
	@NotBlank
	@Size(min=1, max=50)	
	private String lastname;
	
	@NotNull
	@NotEmpty
	@Size(max=512)	
	private String passwordHash;
	
	@NotNull
	@NotBlank
	@Size(min=1, max=50)		
	private String username;
	
	@NotNull
	@Min(0)
	@Max(5)
	private Integer wrongPasswordCounter;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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
		return "EmployeeDto [id=" + id + ", firstname=" + firstname + ", isadmin=" + isadmin + ", lastname=" + lastname			
				+ ", passwordHash=" + passwordHash + ", username=" + username+ ", wrongPasswordCounter=" + wrongPasswordCounter + "]";
	}
}
