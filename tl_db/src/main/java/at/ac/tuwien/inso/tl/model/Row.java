package at.ac.tuwien.inso.tl.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Row implements Serializable{
	private static final long serialVersionUID = 334212542943742103L;

	@Id
	@Column(nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;	
		
	@Column(nullable=false)
	private Integer sequence;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="row")
	private List<Seat> seats;

	@ManyToOne(optional=false)
	@JoinColumn(name="show_id", nullable=false)
	private Show show;

	public Row() {
	}

	public Row(Integer id, Integer sequence, List<Seat> seats, Show show) {
		this.id = id;
		this.sequence = sequence;
		this.seats = seats;
		this.show = show;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}		
}
