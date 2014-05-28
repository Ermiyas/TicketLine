package at.ac.tuwien.inso.tl.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Receipt implements Serializable{
	private static final long serialVersionUID = 7599696540775084248L;

	@Id
	@Column(nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date transactionDate;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable=false)
	private PaymentType paymentType;
	
	@OneToMany(mappedBy="receipt")
	private List<Entry> entries;

	public Receipt() {
	}

	public Receipt(Integer id, Date transactionDate, PaymentType paymentType,
			List<Entry> entries) {
		this.id = id;
		this.transactionDate = transactionDate;
		this.paymentType = paymentType;
		this.entries = entries;
	}

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

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}		
}
