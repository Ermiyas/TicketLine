package at.ac.tuwien.inso.tl.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ArticleDto {
	
	private Integer id;
	
	@Size(max=1024)
	private String description;
	
	@NotNull
	@Size(max=100)	
	private String name;		
	
	@Min(0)	
	private Integer priceInCent;
	
	@Min(0)
	private Integer priceInPoints;	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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
		return "ArticleDto [id=" + id + ", name=" + name + ", priceInCent=" + priceInCent
				+ ", priceInPoints=" + priceInPoints + "]";
	}
}
