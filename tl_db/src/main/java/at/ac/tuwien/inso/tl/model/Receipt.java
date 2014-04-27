package at.ac.tuwien.inso.tl.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Receipt implements Serializable{
	private static final long serialVersionUID = 7599696540775084248L;

	@Id
	@Column(nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date transactionDate;
	
	@Enumerated(EnumType.STRING)
	private TransactionState transactionState;
	
	@ManyToOne
	@JoinColumn(name="cust_id", nullable=false)
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name="methOfPay_id", nullable=false)
	private MethodOfPayment methodOfPayment;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="receipt", fetch=FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<Entry> entries;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public TransactionState getTransactionState() {
		return transactionState;
	}

	public void setTransactionState(TransactionState transactionState) {
		this.transactionState = transactionState;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public MethodOfPayment getMethodOfPayment() {
		return methodOfPayment;
	}

	public void setMethodOfPayment(MethodOfPayment methodOfPayment) {
		this.methodOfPayment = methodOfPayment;
	}

	public List<Entry> getEntry() {
		return entries;
	}

	public void setEntry(List<Entry> entries) {
		this.entries = entries;
	}
}
