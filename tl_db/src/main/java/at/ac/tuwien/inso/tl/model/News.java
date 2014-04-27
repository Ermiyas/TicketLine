package at.ac.tuwien.inso.tl.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class News implements Serializable{
	private static final long serialVersionUID = 4871431571987100831L;

	@Id
	@Column(nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date submittedOn;
	
	@Column(nullable=false, length=255)
	private String title;
	
	@Column(nullable=false, length=1024)
	private String newsText;

	public News(){
	}
	
	public News(Integer id, Date submittedOn, String title, String newsText){
		this.id = id;
		this.submittedOn = submittedOn;
		this.title = title;
		this.newsText = newsText;
	}
	
	public News(Date submittedOn, String title, String newsText){
		this.submittedOn = submittedOn;
		this.title = title;
		this.newsText = newsText;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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
}
