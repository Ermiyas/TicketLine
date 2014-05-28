package at.ac.tuwien.inso.tl.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class EntryDto {
	
	private Integer id;
	
	@NotNull
	@Min(1)	
	private Integer amount;		

	@NotNull
	private Boolean buyWithPoints;	
	
	@NotNull
	private Boolean sold;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Boolean getBuyWithPoints() {
		return buyWithPoints;
	}

	public void setBuyWithPoints(Boolean buyWithPoints) {
		this.buyWithPoints = buyWithPoints;
	}

	public Boolean getSold() {
		return sold;
	}

	public void setSold(Boolean sold) {
		this.sold = sold;
	}	
	
	@Override
	public String toString() {
		return "EntryDto [id=" + id + ", amount=" + amount + ", buyWithPoints=" + buyWithPoints
				+ ", sold=" + sold + "]";
	}
}
