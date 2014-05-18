package at.ac.tuwien.inso.tl.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ArticleDto {
	@Size(max=1024)
	private String description;
	
	@NotNull
	@Size(max=100)	
	private String name;		
	
	@Size(min=0)
	private Integer priceInCent;
	
	@Size(min=0)
	private Integer priceInPoints;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPriceInCent() {
		return priceInCent;
	}

	public void setPriceInCent(Integer priceInCent) {
		this.priceInCent = priceInCent;
	}

	public Integer getPriceInPoints() {
		return priceInPoints;
	}

	public void setPriceInPoints(Integer priceInPoints) {
		this.priceInPoints = priceInPoints;
	}	
	
	@Override
	public String toString() {
		return "ArticleDto [name=" + name + ", priceInCent=" + priceInCent
				+ ", priceInPoints=" + priceInPoints + "]";
	}
}
