package at.ac.tuwien.inso.tl.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class ReceiptDto {

	private Integer id;
	
	@NotNull
	private Date transactionDate;
	
	@NotNull
	private PaymentTypeDto paymentType;	

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
	
	public PaymentTypeDto getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentTypeDto paymentType) {
		this.paymentType = paymentType;
	}

	@Override
	public String toString() {
		return "ReceiptDto [id=" + id + ", transactionDate=" + transactionDate + ", paymentType=" + paymentType + "]";
	}
}
