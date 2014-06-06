package at.ac.tuwien.inso.tl.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class EntryDto {
	
	private Integer id;
	
	@NotNull
	@Min(0)	
	private Integer amount;		

	@NotNull
	private Boolean buyWithPoints;	
	
	@NotNull
	private Boolean sold;

	private Integer articleId;
	
	private Integer ticketId;
	
	// --- Getter & Setter
	
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

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Integer getTicketId() {
		return ticketId;
	}

	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}

	@Override
	public String toString() {
		return "EntryDto [id=" + id + ", amount=" + amount + ", buyWithPoints=" + buyWithPoints
				+ ", sold=" + sold + ", articleId=" + articleId + ", ticketId=" + ticketId + "]";
	}
}
