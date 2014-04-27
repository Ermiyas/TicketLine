package at.ac.tuwien.inso.tl.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {
	@Column(length=50)
	private String street;
	
	@Column(length=25)
	private String postalCode;
	
	@Column(length=50)
	private String city;
	
	@Column(length=50)
	private String country;
	
	public Address(){
	}
	
	public Address(String street, String postalCode, String city, String country){
		this.street = street;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getPostalCode() {
		return postalCode;
	}
	
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
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
}
