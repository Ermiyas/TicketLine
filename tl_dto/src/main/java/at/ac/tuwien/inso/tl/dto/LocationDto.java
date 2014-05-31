package at.ac.tuwien.inso.tl.dto;

import javax.validation.constraints.Size;

public class LocationDto {
	
	private Integer id;
	
	@Size(max=50)	
	private String city;
	
	@Size(max=50)
	private String country;
	
	@Size(max=1024)
	private String description;
	
	@Size(max=25)
	private String postalcode;
	
	@Size(max=50)
	private String street;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}	
	
	@Override
	public String toString() {
		return "LocationDto [id=" + id + ", city=" + city + ", country=" + country
				+ ", description=" + description + ", postalcode=" + postalcode
				+ ", street=" + street + "]";
	}	
}
