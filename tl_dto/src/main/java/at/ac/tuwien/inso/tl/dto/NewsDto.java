package at.ac.tuwien.inso.tl.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NewsDto {

	private Date submittedOn;
	
	@NotNull
	@Size(min=5, max=255)
	private String title;
	
	@NotNull
	@Size(min=5)
	private String newsText;
	
	public Date getSubmittedOn() {
		return submittedOn;
	}
	
	public void setSubmittedOn(Date submittedOn) {
		this.submittedOn = submittedOn;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getNewsText() {
		return newsText;
	}
	
	public void setNewsText(String newsText) {
		this.newsText = newsText;
	}

	@Override
	public String toString() {
		return "NewsDto [submittedOn=" + submittedOn + ", title=" + title
				+ ", newsText=" + newsText + "]";
	}
}
