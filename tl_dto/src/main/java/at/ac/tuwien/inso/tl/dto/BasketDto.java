package at.ac.tuwien.inso.tl.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;


public class BasketDto {
	
	private Integer id;
	
	@NotNull	
	private Date creationdate;

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
	
	@Override
	public String toString() {
		return "BasketDto [id=" + id + ", creationdate=" + creationdate + "]";
	}
}
