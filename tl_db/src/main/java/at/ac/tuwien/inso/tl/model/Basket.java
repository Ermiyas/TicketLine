package at.ac.tuwien.inso.tl.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
public class Basket implements Serializable{
	private static final long serialVersionUID = 4489726236198773L;
	
	@Id
	@Column(nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date creationdate;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="customer_id", nullable=true)
	private Customer customer;

	@OneToMany(mappedBy="basket")
	private List<Entry> entries;

	public Basket() {
	}

	public Basket(Integer id, Date creationdate, Customer customer,
			List<Entry> entries) {
		this.id = id;
		this.creationdate = creationdate;
		this.customer = customer;
		this.entries = entries;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}			
}

