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
public class Seat implements Serializable{
	private static final long serialVersionUID = -5630875224592433118L;
	
	@Id
	@Column(nullable=false, unique=true)	
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;	
	
	@Column(nullable=false)
	private Integer sequence;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="row_id", nullable=false)
	private Row row;
	
	@OneToOne(optional=true)
	@JoinColumn(name="ticket_id", nullable=true)
	private Ticket ticket;

	public Seat() {
	}

	public Seat(Integer id, Integer sequence, Row row, Ticket ticket) {
		this.id = id;
		this.sequence = sequence;
		this.row = row;
		this.ticket = ticket;
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

	public Row getRow() {
		return row;
	}

	public void setRow(Row row) {
		this.row = row;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}		
}

