package at.ac.tuwien.inso.tl.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Employee extends Person{
	private static final long serialVersionUID = 1021211581003682919L;

	private String insuranceNumber;
	
	@Enumerated(EnumType.STRING)
	private Permission permission;
	
	@Temporal(TemporalType.DATE)
	private Date employeedSince;
	
	public Employee(){
	}
	
	public Employee(String firstname, String lastname, String username, String passwordHash){
		setFirstname(firstname);
		setLastname(lastname);
		setUsername(username);
		setPasswordHash(passwordHash);
	}

	public String getInsuranceNumber() {
		return insuranceNumber;
	}

	public void setInsuranceNumber(String insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public Date getEmployeedSince() {
		return employeedSince;
	}

	public void setEmployeedSince(Date employeedSince) {
		this.employeedSince = employeedSince;
	}
}
