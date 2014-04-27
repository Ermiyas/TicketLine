package at.ac.tuwien.inso.tl.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Creditcard extends MethodOfPayment{
	private static final long serialVersionUID = -2150182589389024931L;

	private String owner;
	
	@Column(nullable=false)
	private String creditcardNumber;
	
	@Temporal(TemporalType.DATE)
	private Date validThru;
	
	@Enumerated(EnumType.STRING)
	private CreditcardType creditcardType;

	public Creditcard(){
	}
	
	public Creditcard(Integer id, String creditcardNumber){
		super(id, null);
		this.creditcardNumber = creditcardNumber;
	}
	
	public Creditcard(Integer id, Boolean deleted, String creditcardNumber, String owner, Date validThru, CreditcardType type){
		super(id, deleted);
		this.owner = owner;
		this.creditcardNumber = creditcardNumber;
		this.validThru = validThru;
		this.creditcardType = type;
	}
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getCreditcardNumber() {
		return creditcardNumber;
	}

	public void setCreditcardNumber(String creditcardNumber) {
		this.creditcardNumber = creditcardNumber;
	}

	public Date getValidThru() {
		return validThru;
	}

	public void setValidThru(Date validThru) {
		this.validThru = validThru;
	}

	public CreditcardType getCreditcardType() {
		return creditcardType;
	}

	public void setCreditcardType(CreditcardType creditcardType) {
		this.creditcardType = creditcardType;
	}
}
