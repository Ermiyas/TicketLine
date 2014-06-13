package at.ac.tuwien.inso.tl.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

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
	
	@ManyToMany
	@JoinTable(name="participation", joinColumns={
			@JoinColumn(name="artist_id", nullable=false)},
			inverseJoinColumns = { @JoinColumn(name="performance_id", nullable=false)})	
	private List<Performance> performances;

	public Artist() {
	}

	public Artist(Integer id, String firstname, String lastname,
			List<Performance> performances) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.performances = performances;
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

	public List<Performance> getPerformances() {
		return performances;
	}

	public void setPerformances(List<Performance> performances) {
		this.performances = performances;
	}	
}

