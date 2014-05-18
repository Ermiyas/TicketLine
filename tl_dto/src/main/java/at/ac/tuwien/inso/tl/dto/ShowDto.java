package at.ac.tuwien.inso.tl.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
public class ShowDto {
	
	private Date dateOfPerformance;
	
	@NotNull
	@Size(min=0)
	private Integer priceInCent;
	
	@NotNull
	private String room;

	public Date getDateOfPerformance() {
		return dateOfPerformance;
	}

	public void setDateOfPerformance(Date dateOfPerformance) {
		this.dateOfPerformance = dateOfPerformance;
	}

	public Integer getPriceInCent() {
		return priceInCent;
	}

	public void setPriceInCent(Integer priceInCent) {
		this.priceInCent = priceInCent;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}
	
	@Override
	public String toString() {
		return "ShowDto [dateOfPerformance=" + dateOfPerformance + ", priceInCent=" + priceInCent
				+ ", dateOfPerformance=" + dateOfPerformance + "]";
	}
	
}
