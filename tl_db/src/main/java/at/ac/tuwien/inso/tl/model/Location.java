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
public class Location implements Serializable{
	private static final long serialVersionUID = 9038844425442187230L;

	@Id
	@Column(nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable=false, length=50)
	private String name;
	
	@Column(length=1024)
	private String description;
	
	@Column(length=50)
	private String owner;
	
	private Address address;
	
	@OneToMany(mappedBy="location", cascade=CascadeType.ALL)
	private List<Room> rooms;

	public Location(){
	}
	
	public Location(String name, String description, String owner, Address address){
		this.name = name;
		this.description = description;
		this.owner = owner;
		this.address = address;
	}
	
	public Location(Integer id, String name, String description, String owner, Address address){
		this.id = id;
		this.name = name;
		this.description = description;
		this.owner = owner;
		this.address = address;
	}
	
	public Location(Integer id, String name, String description, String owner, Address address, List<Room> rooms){
		this.id = id;
		this.name = name;
		this.description = description;
		this.owner = owner;
		this.address = address;
		this.rooms = rooms;
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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
}
