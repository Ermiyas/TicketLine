package at.ac.tuwien.inso.tl.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Performance implements Serializable{
	private static final long serialVersionUID = -3795343062479959019L;

	@Id
	@Column(nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable=false, length=50)
	private String name;
	
	private Integer duration;
	
	@Column(length=1024)
	private String description;
	
	@Enumerated(EnumType.STRING)
	private PerformanceType performanceType;
	
	@OneToMany(mappedBy="performance", cascade=CascadeType.ALL)
	private List<Article> articles;
	
	@OneToMany(mappedBy="performance", cascade=CascadeType.ALL)
	private List<Show> shows;
	
	@OneToMany(mappedBy="performance", cascade=CascadeType.ALL)
	private List<Participation> participations;

	public Performance(){
	}
	
	public Performance(String name, String description, Integer duration, PerformanceType type){
		this.name = name;
		this.description = description;
		this.duration = duration;
		this.performanceType = type;
	}
	
	public Performance(Integer id, String name, String description, Integer duration, PerformanceType type){
		this.id = id;
		this.name = name;
		this.description = description;
		this.duration = duration;
		this.performanceType = type;
	}
	
	public Performance(Integer id, String name, String description, Integer duration, PerformanceType type, 
			List<Show> shows, List<Article> articles, List<Participation> participations){
		this.id = id;
		this.name = name;
		this.description = description;
		this.duration = duration;
		this.performanceType = type;
		this.shows = shows;
		this.articles = articles;
		this.participations = participations;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PerformanceType getPerformanceType() {
		return performanceType;
	}

	public void setPerformanceType(PerformanceType performanceType) {
		this.performanceType = performanceType;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public List<Show> getShows() {
		return shows;
	}

	public void setShows(List<Show> shows) {
		this.shows = shows;
	}

	public List<Participation> getParticipations() {
		return participations;
	}

	public void setParticipations(List<Participation> participations) {
		this.participations = participations;
	}
}
