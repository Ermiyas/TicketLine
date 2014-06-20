package at.ac.tuwien.inso.tl.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Customer implements Serializable{
	private static final long serialVersionUID = 4192214529072911981L;
	
	@Id
	@Column(nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(length=50)
	private String city;
	
	@Column(length=50)
	private String country;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date dateOfBirth;
	
	@Column(length=255)
	private String email;
	
	@Column(nullable=false, length=50)
	private String firstname;
	
	@Column
	private Boolean isFemale;		
	
	@Column(nullable=false, length=50)
	private String lastname;
	
	@Column
	private Integer points;
	
	@Column(length=25)
	private String postalcode;
	
	@Column(length=50)
	private String street;
	
	@Column(length=50)
	private String telephonenumber;
	
	@Column(length=25)
	private String title;
	
	@OneToMany(mappedBy="customer")
	private List<Basket> baskets;

	public Customer() {
	}

	public Customer(Integer id, String city, String country, Date dateOfBirth,
			String email, String firstname, Boolean isFemale, String lastname,
			Integer points, String postalcode, String street,
			String telephonenumber, String title, List<Basket> baskets) {
		this.id = id;
		this.city = city;
		this.country = country;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.firstname = firstname;
		this.isFemale = isFemale;
		this.lastname = lastname;
		this.points = points;
		this.postalcode = postalcode;
		this.street = street;
		this.telephonenumber = telephonenumber;
		this.title = title;
		this.baskets = baskets;
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

	public List<Basket> getBaskets() {
		return baskets;
	}

	public void setBaskets(List<Basket> baskets) {
		this.baskets = baskets;
	}

	public boolean isEmpty() {
		if (id != null) return false;
		if (city != null && ! city.trim().equals("")) return false;
		if (country != null && ! country.trim().equals("")) return false;
		if (dateOfBirth != null) return false;
		if (email != null && ! email.trim().equals("")) return false;
		if (firstname != null && ! firstname.trim().equals("")) return false;
		if (isFemale != null) return false;
		if (lastname != null && ! lastname.trim().equals("")) return false;
		if (points != null) return false;
		if (postalcode != null && ! postalcode.trim().equals("")) return false;
		if (street != null && ! street.trim().equals("")) return false;
		if (telephonenumber != null && ! telephonenumber.trim().equals("")) return false;
		if (title != null && ! title.trim().equals("")) return false;
		if (baskets != null) return false;
		return true;
	}

}
