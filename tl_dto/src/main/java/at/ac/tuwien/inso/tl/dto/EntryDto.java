package at.ac.tuwien.inso.tl.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class EntryDto {
	@NotNull
	@Size(min=1)
	private Integer amount;		

	@NotNull
	private Boolean buyWithPoints;	
	
	@NotNull
	private Boolean sold;

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
		return "EntryDto [amount=" + amount + ", buyWithPoints=" + buyWithPoints
				+ ", sold=" + sold + "]";
	}
}
