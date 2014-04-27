package at.ac.tuwien.inso.tl.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Artist implements Serializable{
	private static final long serialVersionUID = -670177989763664076L;
	
	@Id
	@Column(nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable=false, length=50)
	private String firstname;
	
	@Column(nullable=false, length=50)
	private String lastname;
	
	@Column(length=1024)
	private String description;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="artist")
	private List<Participation> participations;

	public Artist(){
	}
	
	public Artist(String firstname, String lastname, String description){
		this.firstname = firstname;
		this.lastname = lastname;
		this.description = description;
	}
	
	public Artist(Integer id, String firstname, String lastname, String description){
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.description = description;
	}
	
	public Artist(Integer id, String firstname, String lastname, String description, List<Participation> participations){
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.description = description;
		this.participations = participations;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Participation> getParticipations() {
		return participations;
	}

	public void setParticipations(List<Participation> participations) {
		this.participations = participations;
	}
}
