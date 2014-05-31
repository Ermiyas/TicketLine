package at.ac.tuwien.inso.tl.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Ticket implements Serializable{
	private static final long serialVersionUID = 2355163364458707580L;

	@Id
	@Column(nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;		
	
	@ManyToOne(optional=true)
	@JoinColumn(name="show_id", nullable=true)
	private Show show;
	
	@OneToOne(mappedBy="ticket", optional=true)
	@JoinColumn(nullable=true)
	private Entry entry;
	
	@OneToOne(mappedBy = "ticket", optional=true)
	@JoinColumn(nullable=true)
	private Seat seat;	

	public Ticket() {
	}

	public Ticket(Integer id, Show show, Entry entry, Seat seat) {
		this.id = id;
		this.show = show;
		this.entry = entry;
		this.seat = seat;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}

	public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}		
}

