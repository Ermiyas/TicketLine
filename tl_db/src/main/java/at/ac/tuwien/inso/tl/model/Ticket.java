package at.ac.tuwien.inso.tl.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Ticket implements Serializable{
	private static final long serialVersionUID = 2355163364458707580L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable=true)
	private String description;
	
	@Column(nullable=false)
	private Integer price;
	
	@ManyToOne
	@JoinColumn(name="show_id", nullable=false)
	private Show show;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="seat_id", nullable=false)
	private Seat seat;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="ticket", fetch=FetchType.EAGER)
	@Fetch(value=FetchMode.SUBSELECT)
	private List<TicketIdentifier> ticketIdentifiers;

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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public List<TicketIdentifier> getTicketIdentifiers() {
		return ticketIdentifiers;
	}

	public void setTicketIdentifiers(List<TicketIdentifier> ticketIdentifiers) {
		this.ticketIdentifiers = ticketIdentifiers;
	}
}
