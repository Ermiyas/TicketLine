package at.ac.tuwien.inso.tl.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class News implements Serializable{
	private static final long serialVersionUID = 4871431571987100831L;

	@Id
	@Column(nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(nullable=false, length=1024)
	private String newsText;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date submittedOn;
	
	@Column(nullable=false, length=255)
	private String title;
	
	@ManyToMany
	@JoinTable(name="newsread", joinColumns={
			@JoinColumn(name="news_id", nullable=false)},
			inverseJoinColumns = { @JoinColumn(name="employee_id", nullable=false)})	
	private List<Employee> readBy;

	public News() {
	}	
	
	public News(Date submittedOn, String title, String newsText) {
		this.newsText = newsText;
		this.submittedOn = submittedOn;
		this.title = title;
	}		

	public News(Integer id, Date submittedOn, String title, String newsText) {
		this.id = id;
		this.newsText = newsText;
		this.submittedOn = submittedOn;
		this.title = title;
	}

	public News(Integer id, String newsText, Date submittedOn, String title,
			List<Employee> readBy) {
		this.id = id;
		this.newsText = newsText;
		this.submittedOn = submittedOn;
		this.title = title;
		this.readBy = readBy;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNewsText() {
		return newsText;
	}

	public void setNewsText(String newsText) {
		this.newsText = newsText;
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

	public List<Employee> getReadBy() {
		return readBy;
	}

	public void setReadBy(List<Employee> readBy) {
		this.readBy = readBy;
	}	
}
