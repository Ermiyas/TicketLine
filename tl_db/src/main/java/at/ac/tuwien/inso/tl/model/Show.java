package at.ac.tuwien.inso.tl.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
	@Column(nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;		
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date dateOfPerformance;
	
	@Column(nullable=false)
	private Integer priceInCent;
	
	@Column(nullable=false)
	private String room;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="location_id", nullable=false)
	private Location location;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="performance_id", nullable=false)
	private Performance performance;
	
	@OneToMany(mappedBy="show")
	private List<Ticket> tickets;
	
	@OneToMany(mappedBy="show")
	private Set<Row> rows;

	public Show() {
	}

	public Show(Integer id, Date dateOfPerformance, Integer priceInCent,
			String room, Location location, Performance performance,
			List<Ticket> tickets, Set<Row> rows) {
		this.id = id;
		this.dateOfPerformance = dateOfPerformance;
		this.priceInCent = priceInCent;
		this.room = room;
		this.location = location;
		this.performance = performance;
		this.tickets = tickets;
		this.rows = rows;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateOfPerformance() {
		return dateOfPerformance;
	}

	public void setDateOfPerformance(Date dateOfPerformance) {
		this.dateOfPerformance = dateOfPerformance;
	}

	public Integer getPriceInCent() {
		return priceInCent;
	}

	public void setPriceInCent(Integer priceInCent) {
		this.priceInCent = priceInCent;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Performance getPerformance() {
		return performance;
	}

	public void setPerformance(Performance performance) {
		this.performance = performance;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public Set<Row> getRows() {
		return rows;
	}

	public void setRows(Set<Row> rows) {
		this.rows = rows;
	}	
}
