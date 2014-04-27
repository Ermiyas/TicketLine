package at.ac.tuwien.inso.tl.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Show implements Serializable{
	private static final long serialVersionUID = 2398987661302928431L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private Boolean canceled;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfPerformance;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="show")
	private List<Ticket> tickets;
	
	@ManyToOne
	@JoinColumn(name="room_id", nullable=false)
	private Room room;
	
	@ManyToOne
	@JoinColumn(name="perf_id", nullable=false)
	private Performance performance;

	public Show(){
	}
	
	public Show(Boolean canceled, Date date, Room room, Performance performance){
		this.canceled = canceled;
		this.dateOfPerformance = date;
		this.room = room;
		this.performance = performance;
	}
	
	public Show(Integer id, Boolean canceled, Date date, Room room, Performance performance){
		this.id = id;
		this.canceled = canceled;
		this.dateOfPerformance = date;
		this.room = room;
		this.performance = performance;
	}
	
	public Show(Integer id, Boolean canceled, Date date, Room room, Performance performance, List<Ticket> tickets){
		this.id = id;
		this.canceled = canceled;
		this.dateOfPerformance = date;
		this.room = room;
		this.performance = performance;
		this.tickets = tickets;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getCanceled() {
		return canceled;
	}

	public void setCanceled(Boolean canceled) {
		this.canceled = canceled;
	}

	public Date getDateOfPerformance() {
		return dateOfPerformance;
	}

	public void setDateOfPerformance(Date dateOfPerformance) {
		this.dateOfPerformance = dateOfPerformance;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Performance getPerformance() {
		return performance;
	}

	public void setPerformance(Performance performance) {
		this.performance = performance;
	}
}
