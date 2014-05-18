package at.ac.tuwien.inso.tl.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class ReceiptDto {

	@NotNull
	private Date transactionDate;
	
	@NotNull
	private PaymentTypeDto paymentType;	
	
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
		return "ReceiptDto [transactionDate=" + transactionDate + ", paymentType=" + paymentType + "]";
	}
}
