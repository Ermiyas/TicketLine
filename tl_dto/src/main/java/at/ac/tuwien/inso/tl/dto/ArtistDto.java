package at.ac.tuwien.inso.tl.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ArtistDto {	
	
	private Integer id;
	
	@NotNull
	@Size(max=50)	
	private String firstname;
	
	@NotNull
	@Size(max=50)	
	private String lastname;
	
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

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}	
	
	@Override
	public String toString() {
		return "ArtistDto [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + "]";
	}
}
