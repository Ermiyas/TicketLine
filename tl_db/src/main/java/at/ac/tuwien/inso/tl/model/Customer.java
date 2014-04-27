package at.ac.tuwien.inso.tl.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Customer extends Person{
	private static final long serialVersionUID = 4192214529072911981L;

	private Boolean blocked;
	
	@Enumerated(EnumType.STRING)
	private CustomerGroup customerGroup;
	
	private String ticketcardNumber;
	
	@Temporal(TemporalType.DATE)
	private Date ticketcardValidThru;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="customer", fetch=FetchType.EAGER)
	private List<MethodOfPayment> methodOfPayments;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="customer")
	private List<Receipt> receipts;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="customer")
	private List<Reservation> reservations;

	public Customer(){
	}
	
	public Customer(Integer id, Sex sex, String title, String firstname, String lastname, String email, 
			String telefonNumber, Date dateOfBirth, Boolean blocked, CustomerGroup customerGroup, 
			String ticketcardNumber, Date ticketcardValidThru){
		super(id, sex, title, firstname, lastname, email, telefonNumber, dateOfBirth);
		this.blocked = blocked;
		this.customerGroup = customerGroup;
		this.ticketcardNumber = ticketcardNumber;
		this.ticketcardValidThru = ticketcardValidThru;
	}
	
	public Customer(Integer id, Sex sex, String title, String firstname, String lastname, String email, 
			String telefonNumber, Date dateOfBirth, Address address, Boolean blocked, CustomerGroup customerGroup,
			String ticketcardNumber, Date ticketcardValidThru){
		super(id, sex, title, firstname, lastname, email, telefonNumber, dateOfBirth, address);
		this.blocked = blocked;
		this.customerGroup = customerGroup;
		this.ticketcardNumber = ticketcardNumber;
		this.ticketcardValidThru = ticketcardValidThru;
	}
	
	public Boolean getBlocked() {
		return blocked;
	}

	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}

	public CustomerGroup getCustomerGroup() {
		return customerGroup;
	}

	public void setCustomerGroup(CustomerGroup customerGroup) {
		this.customerGroup = customerGroup;
	}

	public String getTicketcardNumber() {
		return ticketcardNumber;
	}

	public void setTicketcardNumber(String ticketcardNumber) {
		this.ticketcardNumber = ticketcardNumber;
	}

	public Date getTicketcardValidThru() {
		return ticketcardValidThru;
	}

	public void setTicketcardValidThru(Date ticketcardValidThru) {
		this.ticketcardValidThru = ticketcardValidThru;
	}

	public List<MethodOfPayment> getMethodOfPayments() {
		return methodOfPayments;
	}

	public void setMethodOfPayments(List<MethodOfPayment> methodOfPayments) {
		this.methodOfPayments = methodOfPayments;
	}

	public List<Receipt> getReceipts() {
		return receipts;
	}

	public void setReceipts(List<Receipt> receipts) {
		this.receipts = receipts;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
}
