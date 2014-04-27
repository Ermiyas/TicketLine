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
public class Seat implements Serializable{
	private static final long serialVersionUID = -5630875224592433118L;
	
	@Id
	@Column(name ="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String name;
	
	private String description;
	
	@Column(nullable=false, name="sequence")
	private Integer order;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="room_id", nullable=false)
	private Room room;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cat_id", nullable=false)
	private Category category;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="row_id", nullable=false)
	private Row row;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="gallery_id", nullable=true)
	private Gallery gallery;
	
//	private Coordinate coordinate;

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

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Row getRow() {
		return row;
	}

	public void setRow(Row row) {
		this.row = row;
	}

	public Gallery getGallery() {
		return gallery;
	}

	public void setGallery(Gallery gallery) {
		this.gallery = gallery;
	}

//	public Coordinate getCoordinate() {
//		return coordinate;
//	}
//
//	public void setCoordinate(Coordinate coordinate) {
//		this.coordinate = coordinate;
//	}
}
