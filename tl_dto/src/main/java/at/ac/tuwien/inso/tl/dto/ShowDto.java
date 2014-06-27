package at.ac.tuwien.inso.tl.dto;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
public class ShowDto {
	
	private Integer id;
	
	private Date dateOfPerformance;
	
	@NotNull
	@Min(0)
	private Integer priceInCent;
	
	@NotNull
	private String room;
	
	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	@NotNull
	private Integer performanceId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
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
	
	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	public Integer getPerformanceId() {
		return performanceId;
	}

	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	public void setPerformanceId(Integer performanceId) {
		this.performanceId = performanceId;
	}

	@Override
	public String toString() {
		return "ShowDto [id=" + id + ", dateOfPerformance=" + dateOfPerformance + ", priceInCent=" + priceInCent
				+ ", room=" + room + ", performanceId=" + performanceId + "]";
	}
	
}
