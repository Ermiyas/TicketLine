package at.ac.tuwien.inso.tl.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class BankAccount extends MethodOfPayment{
	private static final long serialVersionUID = -5767470592696077401L;

	private String owner;
	
	@Column(nullable=false)
	private String bank;
	
	@Column(nullable=false)
	private String accountNumber;
	
	@Column(nullable=false)
	private String bankCode;
	
	private String IBAN;
	
	private String BIC;

	public BankAccount(){
	}
	
	public BankAccount(Integer id, String accountNumber, String bankCode){
		super(id, null);
		this.accountNumber = accountNumber;
		this.bankCode = bankCode;
	}
	
	public BankAccount(Integer id, Boolean deleted, String bank, String accountNumber, String bankCode,
			String iban, String bic){
		super(id, deleted);
		this.bank = bank;
		this.accountNumber = accountNumber;
		this.bankCode = bankCode;
		this.IBAN = iban;
		this.BIC = bic;
	}
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getIBAN() {
		return IBAN;
	}

	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}

	public String getBIC() {
		return BIC;
	}

	public void setBIC(String bIC) {
		BIC = bIC;
	}
}
