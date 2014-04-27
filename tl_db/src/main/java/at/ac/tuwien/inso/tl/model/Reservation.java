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
public class Reservation implements Serializable{
	private static final long serialVersionUID = 6362706103122264420L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable=false, unique=true)
	private Integer reservationNumber;
	
	@ManyToOne
	@JoinColumn(name="cust_id", nullable=false)
	private Customer customer;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="reservation", fetch=FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<TicketIdentifier> ticketIdentifiers;

	public Reservation(){
	}
	
	public Reservation(Integer id, Integer reservationNumber){
		this.id = id;
		this.reservationNumber = reservationNumber;
	}
	
	public Reservation(Integer id, Integer reservationNumber, Customer customer){
		this.id = id;
		this.reservationNumber = reservationNumber;
		this.customer = customer;
	}
	
	public Reservation(Integer id, Integer reservationNumber, Customer customer, List<TicketIdentifier> ticketIdentifiers){
		this.id = id;
		this.reservationNumber = reservationNumber;
		this.customer = customer;
		this.ticketIdentifiers = ticketIdentifiers;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getReservationNumber() {
		return reservationNumber;
	}

	public void setReservationNumber(Integer reservationNumber) {
		this.reservationNumber = reservationNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<TicketIdentifier> getTicketIdentifiers() {
		return ticketIdentifiers;
	}

	public void setTicketIdentifiers(List<TicketIdentifier> ticketIdentifiers) {
		this.ticketIdentifiers = ticketIdentifiers;
	}
}
