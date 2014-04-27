package at.ac.tuwien.inso.tl.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Participation implements Serializable{
	private static final long serialVersionUID = 3071965473717124011L;

	@Id
	@Column(nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(length=1024)
	private String description;
	
	@Column(length=50)
	private String artistRole;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="perf_id", nullable=false)
	private Performance performance;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="artist_id", nullable=false)
	private Artist artist;

	public Participation(){
	}
	
	public Participation(String artistRole, String description, Performance performance, Artist artist){
		this.artistRole = artistRole;
		this.description = description;
		this.performance = performance;
		this.artist = artist;
	}
	
	public Participation(Integer id, String artistRole, String description, Performance performance, Artist artist){
		this.id = id;
		this.artistRole = artistRole;
		this.description = description;
		this.performance = performance;
		this.artist = artist;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getArtistRole() {
		return artistRole;
	}

	public void setArtistRole(String artistRole) {
		this.artistRole = artistRole;
	}

	public Performance getPerformance() {
		return performance;
	}

	public void setPerformance(Performance performance) {
		this.performance = performance;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}
}
