/**
 * 
 */
package at.ac.tuwien.inso.tl.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class Person implements Serializable{
	private static final long serialVersionUID = -1102929654096272086L;

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable=false)
	private String firstname;
	
	@Column(nullable=false)
	private String lastname;
	
	@Column(nullable=true)
	private String title;
	
	private Address address;
	
	@Enumerated(value=EnumType.STRING)
	private Sex sex;
	
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	
	private String email;
	
	private String telefonNumber;
	
	private String username;
	
	private String passwordHash;

	public Person(){
	}
	
	public Person(Integer id, Sex sex, String title, String firstname, String lastname, String email, 
			String telefonNumber, Date dateOfBirth){
		this.id = id; 
		this.sex = sex;
		this.title = title;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.telefonNumber = telefonNumber;
		this.dateOfBirth = dateOfBirth;
	}
	
	public Person(Integer id, Sex sex, String title, String firstname, String lastname, String email, 
			String telefonNumber, Date dateOfBirth, Address address){
		this.id = id; 
		this.sex = sex;
		this.title = title;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.telefonNumber = telefonNumber;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
	}
	
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
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

	public String getTelefonNumber() {
		return telefonNumber;
	}

	public void setTelefonNumber(String telefonNumber) {
		this.telefonNumber = telefonNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
}
