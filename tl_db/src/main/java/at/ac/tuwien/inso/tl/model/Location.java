package at.ac.tuwien.inso.tl.model;

import java.io.Serializable;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Location implements Serializable {
	private static final long serialVersionUID = 8754121984126397845L;

	@Id
	@Column(nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(length=50)
	private String city;
	
	@Column(length=50)
	private String country;
	
	@Column(length=1024)
	private String description;
	
	@Column(length=25)
	private String postalcode;
	
	@Column(length=50)
	private String street;
	
	@OneToMany(mappedBy="location")	
	private List<Show> shows;

	public Location() {
	}

	public Location(Integer id, String city, String country,
			String description, String postalcode, String street,
			List<Show> shows) {
		this.id = id;
		this.city = city;
		this.country = country;
		this.description = description;
		this.postalcode = postalcode;
		this.street = street;
		this.shows = shows;
	}

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

	public List<Show> getShows() {
		return shows;
	}

	public void setShows(List<Show> shows) {
		this.shows = shows;
	}	
}


