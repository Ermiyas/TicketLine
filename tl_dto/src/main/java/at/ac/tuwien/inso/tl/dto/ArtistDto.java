package at.ac.tuwien.inso.tl.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ArtistDto {	
	@NotNull
	@Size(max=50)	
	private String firstname;
	
	@NotNull
	@Size(max=50)	
	private String lastname;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}	
	
	@Override
	public String toString() {
		return "ArtistDto [firstname=" + firstname + ", lastname=" + lastname + "]";
	}
}
