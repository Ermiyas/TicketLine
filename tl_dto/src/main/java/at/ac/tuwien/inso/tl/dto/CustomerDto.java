package at.ac.tuwien.inso.tl.dto;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

public class CustomerDto {
	
	private Integer id;
	
	@Size(max=50)
	private String city;
	
	@Size(max=50)
	private String country;
	
	@NotNull
	@Past
	private Date dateOfBirth;
	
	@Size(max=255)
	private String email;
	
	@NotNull
	@Size(max=50)
	private String firstname;
	
	@NotNull
	private Boolean isFemale;		
	
	@NotNull
	@Size(max=50)
	private String lastname;
	
	@Min(0)
	private Integer points;
	
	@Size(max=25)
	private String postalcode;
	
	@Size(max=50)
	private String street;
	
	@Size(max=50)
	private String telephonenumber;
	
	@Size(max=25)
	private String title;	

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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public Boolean getIsFemale() {
		return isFemale;
	}

	public void setIsFemale(Boolean isFemale) {
		this.isFemale = isFemale;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
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

	public String getTelephonenumber() {
		return telephonenumber;
	}

	public void setTelephonenumber(String telephonenumber) {
		this.telephonenumber = telephonenumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}	
	
	@Override
	public String toString() {
		return "CustomerDto [id=" + id + ", city=" + city + ", country=" + country + ", dateOfBirth=" + dateOfBirth
				+ ", email=" + email + ", firstname=" + firstname + ", isFemale=" + isFemale
				+ ", lastname=" + lastname + ", points=" + points + ", postalcode=" + postalcode
				+ ", street=" + street + ", telephonenumber=" + telephonenumber+ ", title=" + title + "]";
	}
}
