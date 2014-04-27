package at.ac.tuwien.inso.tl.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Room implements Serializable{
	private static final long serialVersionUID = -668105317259567139L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(length=50)
	private String name;
	
	@Column(length=1024)
	private String description;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="room")
	private List<Show> shows;
	
	@ManyToOne
	@JoinColumn(name="loc_id", nullable=false)
	private Location location;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="room")
	private List<Seat> seats;

	public Room(){
	}
	
	public Room(String name, String description, Location location){
		this.name = name;
		this.description = description;
		this.location = location;
	}
	
	public Room(Integer id, String name, String description, Location location){
		this.id = id;
		this.name = name;
		this.description = description;
		this.location = location;
	}
	
	public Room(Integer id, String name, String description, Location location, List<Show> shows, List<Seat> seats){
		this.id = id;
		this.name = name;
		this.description = description;
		this.location = location;
		this.shows = shows;
		this.seats = seats;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Show> getShows() {
		return shows;
	}

	public void setShows(List<Show> shows) {
		this.shows = shows;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}
}
