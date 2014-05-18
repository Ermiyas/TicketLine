package at.ac.tuwien.inso.tl.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PerformanceDto {
	
	@Size(max=1024)
	private String content;
	
	@NotNull
	@Size(max=50)
	private String description;
	
	@Size(min=0)
	private Integer durationInMinutes;

	@Size(max=50)
	private String performancetype;	
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getDurationInMinutes() {
		return durationInMinutes;
	}
	
	public void setDurationInMinutes(Integer durationInMinutes) {
		this.durationInMinutes = durationInMinutes;
	}
	
	public String getPerformancetype() {
		return performancetype;
	}

	public void setPerformancetype(String performancetype) {
		this.performancetype = performancetype;
	}

	@Override
	public String toString() {
		return "PerformanceDto [content=" + content + ", description=" + description
				+ ", durationInMinutes=" + durationInMinutes + ", performancetype=" + performancetype + "]";
	}
}
