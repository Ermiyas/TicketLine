package at.ac.tuwien.inso.tl.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;


public class BasketDto {	
	@NotNull	
	private Date creationdate;

	public Date getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}	
	
	@Override
	public String toString() {
		return "BasketDto [creationdate=" + creationdate + "]";
	}
}
