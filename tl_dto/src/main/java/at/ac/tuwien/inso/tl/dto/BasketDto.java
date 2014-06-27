package at.ac.tuwien.inso.tl.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;


public class BasketDto {
	
	private Integer id;
	
	@NotNull	
	private Date creationdate;
	
	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	private Integer customerId;

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
	
	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	public Integer getCustomerId() {
		return customerId;
	}

	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "BasketDto [id=" + id + ", creationdate=" + creationdate + ", customerId=" + customerId + "]";
	}
}
